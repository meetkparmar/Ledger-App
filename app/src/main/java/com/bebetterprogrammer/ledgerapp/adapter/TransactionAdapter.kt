package com.bebetterprogrammer.ledgerapp.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bebetterprogrammer.ledgerapp.database.Transaction

class TransactionAdapter(val onClick: (Int) -> Unit) : RecyclerView.Adapter<TransactionViewHolder>() {

    var data = listOf<Transaction>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder.create(parent)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, position, onClick)

    }
}