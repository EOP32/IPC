package com.example.rpcproject.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.rpcproject.IPhonebookInterface
import com.example.rpcproject.data.Phonebook

class PhonebookService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return object : IPhonebookInterface.Stub() {
            override fun getContacts(): List<String> {
               return Phonebook.getAllContactsInString()
            }

            override fun searchContacts(searchString: String): List<String> {
                return Phonebook.searchForContactsForRemoteCall(searchString)
            }

            override fun addContact(fName: String, lName: String, number: String) {
                Phonebook.addContact(fName, lName, number)
            }
        }
    }
}