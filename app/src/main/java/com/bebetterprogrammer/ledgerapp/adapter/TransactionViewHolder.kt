package com.bebetterprogrammer.ledgerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bebetterprogrammer.ledgerapp.R
import com.bebetterprogrammer.ledgerapp.database.Transaction

class TransactionViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val name = view.findViewById<TextView>(R.id.tv_name)
    private val details = view.findViewById<TextView>(R.id.tv_details)
    private val amount = view.findViewById<TextView>(R.id.tv_amount)
    private val date= view.findViewById<TextView>(R.id.tv_date)

    private var transaction: Transaction? = null

    fun bind(
        transaction: Transaction,
        position: Int,
        onClick: (Int) -> Unit
    ) {
        name.text = transaction.userName
        details.text = transaction.details
        amount.text = transaction.amount
        date.text = transaction.date

        view.setOnLongClickListener {
            onClick.invoke(position)
            return@setOnLongClickListener true
        }
    }


    companion object {
        fun create(parent: ViewGroup): TransactionViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.transaction_detail_item, parent, false)
            return TransactionViewHolder(view)
        }
    }

}
