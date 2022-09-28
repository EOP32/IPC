package com.example.rpcproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.rpcproject.R
import com.example.rpcproject.data.Phonebook

class AddFragment: Fragment() {
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var phoneNumber: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstName = view.findViewById(R.id.add_name)
        lastName = view.findViewById(R.id.add_lastname)
        phoneNumber = view.findViewById(R.id.add_number)

        view.findViewById<Button>(R.id.add_save).setOnClickListener {
            addNewContact()
        }
    }

    private fun addNewContact() {
        val fname = firstName.text.toString().trim()
        val lname = lastName.text.toString().trim()
        val number = phoneNumber.text.toString().trim()

        if (fname.isBlank() || lname.isBlank() || number.isBlank()
            || fname.length < 3 || lname.length < 3 || phoneNumber.length() < 5) {
            Toast.makeText(requireContext(), "First name, last name or number is too short", Toast.LENGTH_SHORT).show()
            return
        } else {
            Phonebook.addContact(fname, lname, number)
            findNavController().navigate(R.id.action_global_mainFragment)
        }
    }
}