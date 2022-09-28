package com.example.rpcproject.data

object Phonebook {
    val contacts: MutableList<Contact> = mutableListOf(
        Contact("Adam", "Adam", "12345"),
        Contact("Eva", "Eva", "34555"),
        Contact("Eva", "Adam", "12355"),
        Contact("Ooo", "Worm", "22245"),
        Contact("Trer", "Adam", "12333")
    )

    fun addContact(firstName: String, lastName: String, number: String) {
        synchronized(contacts) {
            contacts.forEach {
                if (it.firstName.lowercase() == firstName.lowercase() && it.lastName.lowercase() == lastName.lowercase()) {
                    it.addNumber(number)
                    return
                }
            }
            contacts.add(Contact(firstName, lastName, number))
        }
    }

    fun searchForContacts(searchPhrase: String): List<Contact> {
        val resultList: MutableList<Contact> = mutableListOf()

        synchronized(contacts) {
            contacts.forEach {
                if (it.firstName.lowercase().contains(searchPhrase.lowercase())
                    || it.lastName.lowercase().contains(searchPhrase.lowercase())
                ) {
                    it.incrementGet()
                    resultList.add(it)
                }
            }
        }

        return resultList
    }

    fun getTopThreeContacts(): List<Contact> {
        synchronized(contacts) {
            contacts.sortByDescending { it.numberOfReads }
        }
        return contacts.take(3)
    }

    fun searchForContactsForRemoteCall(searchPhrase: String): List<String> {
        val result = searchForContacts(searchPhrase)

        val resultList = result.map {
            "${it.firstName} ${it.lastName} ${getNumbersString(it)} ${it.numberOfReads}"
        }
        return resultList
    }

    fun getAllContactsInString(): List<String> {
        synchronized(contacts) {
            val result = contacts.map {
                "${it.firstName} ${it.lastName} ${getNumbersString(it)} ${it.numberOfReads}"
            }
            return result
        }
    }

    private fun getNumbersString(c: Contact): String {
        var numbers = ""

        c.numbers.forEach {
            numbers += "$it "
        }
        return numbers
    }
}