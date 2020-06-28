package com.bebetterprogrammer.ledgerapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bebetterprogrammer.ledgerapp.R
import com.bebetterprogrammer.ledgerapp.database.Transaction
import com.bebetterprogrammer.ledgerapp.utils.Status

class TransactionViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val name = view.findViewById<TextView>(R.id.tv_name)
    private val details = view.findViewById<TextView>(R.id.tv_details)
    private val amount = view.findViewById<TextView>(R.id.tv_amount)
    private val date = view.findViewById<TextView>(R.id.tv_date)
    private val status = view.findViewById<TextView>(R.id.tv_status)

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
        status.text = transaction.status.name

        if (transaction.status == Status.APPROVED) {
            status.setTextColor(view.resources.getColor(android.R.color.holo_green_dark))
        } else if (transaction.status == Status.REJECTED) {
            status.setTextColor(view.resources.getColor(android.R.color.holo_red_dark))
        } else {
            status.setTextColor(view.resources.getColor(android.R.color.holo_orange_dark))
        }

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
