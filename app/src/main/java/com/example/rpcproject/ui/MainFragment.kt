package com.example.rpcproject.ui

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rpcproject.R
import com.example.rpcproject.data.Contact
import com.example.rpcproject.data.Phonebook
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {
    private lateinit var contactsRecyclerView: RecyclerView
    private lateinit var mainAdapter: MainAdapter
    private lateinit var searchText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchText = view.findViewById(R.id.main_search)
        mainAdapter = MainAdapter()

        contactsRecyclerView = view.findViewById<RecyclerView>(R.id.main_recyclerview).apply { adapter = mainAdapter }
        contactsRecyclerView.layoutManager = LinearLayoutManager(requireContext()).apply {
            contactsRecyclerView.addItemDecoration(
                DividerItemDecoration(requireContext(), orientation)
            )
        }

        loadContacts()

        view.findViewById<FloatingActionButton>(R.id.main_add).setOnClickListener {
            findNavController().navigate(R.id.action_global_addFragment)
        }

        view.findViewById<FloatingActionButton>(R.id.main_top_or_all).setOnClickListener {
            getMostSearched()
        }

        view.findViewById<Button>(R.id.main_search_button).setOnClickListener {
            findContacts()
        }

        view.findViewById<FloatingActionButton>(R.id.main_duplicate).setOnClickListener {
            mergeContacts()
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (mainAdapter.getSizeOfContacts() < Phonebook.contacts.size) {
                        mainAdapter.setContacts(Phonebook.contacts)
                    } else {
                        if (isEnabled) {
                            isEnabled = false
                            requireActivity().onBackPressed()
                        }
                    }
                }
            })
    }

    private fun mergeContacts() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch(Dispatchers.Default) {
                val result = Phonebook.merge()

                withContext(Dispatchers.Main) {
                    mainAdapter.setContacts(Phonebook.contacts)

                    if (result == 0) {
                        Toast.makeText(requireContext(), "No duplicates found", Toast.LENGTH_SHORT).show()
                    } else {
                        Snackbar.make(requireView(), "$result duplicates combined", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun findContacts() {
        val searchString = searchText.text.toString()
        if (searchString.isBlank() || searchString.length < 3) {
            Toast.makeText(requireContext(), "You need to type in at least 3 letters!", Toast.LENGTH_SHORT).show()
            return
        }

        viewLifecycleOwner.lifecycleScope.launch {
            launch(Dispatchers.Default) {
                val contacts = Phonebook.searchForContacts(searchString)

                withContext(Dispatchers.Main) {
                    mainAdapter.setContacts(contacts)
                }
            }
        }
    }

    private fun loadContacts() = mainAdapter.setContacts(Phonebook.contacts)

    private fun getMostSearched() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch(Dispatchers.Default) {
                val contacts = Phonebook.getTopThreeContacts()

                withContext(Dispatchers.Main) {
                    mainAdapter.setContacts(contacts)
                }
            }
        }
    }
}