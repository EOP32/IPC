package com.example.rpcproject;

interface IPhonebookInterface {
    List<String> getContacts();
    List<String> searchContacts(String searchString);
    void addContact(String fName, String lName, String number);
}