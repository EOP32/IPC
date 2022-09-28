package com.example.rpcproject.data

data class Contact(
    val firstName: String,
    val lastName: String,
    private val number: String = "",
    val numbers: MutableList<String> = mutableListOf()
) {
    var numberOfReads: Int = 0
    var fullName = ""

    init {
        fullName = "$firstName $lastName"
        if (number.isNotBlank()) numbers.add(number)
    }

    fun addNumber(number: String) {
        if (numbers.contains(number)) {
            return
        }
        numbers.add(number)
    }

    fun incrementGet() {
        numberOfReads++
    }

    fun incrementGetBy(num: Int) {
        numberOfReads += num
    }

    override fun toString(): String {
        return fullName
    }
}