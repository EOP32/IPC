package com.example.rpcproject.data

data class Contact(
    val firstName: String,
    val lastName: String,
    val number: String = "",
    val numbers: MutableList<String> = mutableListOf()
) {
    var numberOfReads: Int = 0

    init {
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
}