package com.example.rpcproject.data

object Phonebook {
    val contacts: MutableList<Contact> = mutableListOf(
        Contact("Adam", "Adam", "11111"),
        Contact("Adam", "Adam", "12345"),
        Contact("Adam", "Adam", "22222"),
        Contact("Eva", "Eva", "34555"),
        Contact("Eva", "Adam", "12355"),
        Contact("Ooo", "Worm", "22245"),
        Contact("Trer", "Adam", "12333")
    )

    fun addContact(firstName: String, lastName: String, number: String) {
        synchronized(contacts) {
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

    fun merge(): Int {
        var counter: Int

        synchronized(contacts) {
            val groupedCollection = contacts.groupBy { it.toString() }
            return if (groupedCollection.size == contacts.size) {
                0
            } else {
                counter = contacts.size - groupedCollection.size +1
                groupedCollection.values.toMutableList().forEach {
                    if (it.size == 1) {
                        return@forEach
                    }

                    val contact = helpMerge(it)
                    contacts.removeAll {
                        it.firstName.lowercase() == contact.firstName.lowercase() &&
                                it.lastName.lowercase() == contact.lastName.lowercase()
                    }
                    contacts.add(contact)
                }

                counter
            }
        }
    }

    private fun helpMerge(list: List<Contact>): Contact {
        val contact = list[0]
        var counter = 0

        for (element in list) {
            if (counter != 0) {
                contact.incrementGetBy(element.numberOfReads)
            }
            for (num in element.numbers) {
                contact.addNumber(num)
            }
            counter++
        }
        return contact
    }
}