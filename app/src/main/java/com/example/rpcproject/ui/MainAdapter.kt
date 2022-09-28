package com.example.rpcproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rpcproject.R
import com.example.rpcproject.data.Contact

class MainAdapter(
    private var listOfContacts: List<Contact> = emptyList()
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = listOfContacts[position]

        holder.itemView.findViewById<TextView>(R.id.item_name).text = "${contact.firstName} ${contact.lastName}"
        setNumberText(holder.itemView.findViewById(R.id.item_number), contact.numbers)
        holder.itemView.findViewById<TextView>(R.id.item_counter).text = contact.numberOfReads.toString()
    }

    override fun getItemCount(): Int {
        return listOfContacts.size
    }

    fun setContacts(contacts: List<Contact>) {
        listOfContacts = contacts
        notifyDataSetChanged()
    }

    private fun setNumberText(tv: TextView, numbersList: List<String>) {
        var number = ""
        numbersList.forEachIndexed { index, num ->
            if (index == 0) {
                number = num
            } else {
                number = "$number, $num"
            }
        }

        tv.text = number
    }

    fun getSizeOfContacts() = listOfContacts.size
}