package com.bebetterprogrammer.ledgerapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bebetterprogrammer.ledgerapp.R
import com.bebetterprogrammer.ledgerapp.adapter.TransactionAdapter
import com.bebetterprogrammer.ledgerapp.database.Transaction
import com.bebetterprogrammer.ledgerapp.ui.LoginActivity.Companion.TAG
import com.bebetterprogrammer.ledgerapp.utils.Status
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.android.synthetic.main.dialog_box.view.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    lateinit var currentView: View
    lateinit var adapter: TransactionAdapter
    lateinit var database: DatabaseReference
    val transactionList = mutableListOf<Transaction>()
    var valueListner: ValueEventListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        database = FirebaseDatabase.getInstance().reference
        currentView = inflater.inflate(R.layout.fragment_home, container, false)
        return currentView
    }

    override fun onStart() {
        super.onStart()
        valueListner = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                transactionList.clear()
                for (data in dataSnapshot.children) {
                    val transaction = data.getValue<Transaction>()
                    transaction?.let { transactionList.add(it) }
                }

                if (transactionList.isEmpty()) {
                    tv_empty_list.visibility = View.VISIBLE
                    rv_transaction_history.visibility = View.GONE
                } else {
                    tv_empty_list.visibility = View.GONE
                    rv_transaction_history.visibility = View.VISIBLE
                    adapter.data = transactionList
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        valueListner?.let {
            database.addValueEventListener(it)
        }
    }

    override fun onStop() {
        valueListner?.let {
            database.removeEventListener(it)
        }
        super.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        rv_transaction_history.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = TransactionAdapter { position ->
            onTransactionClicked(position)
        }
        rv_transaction_history.adapter = adapter
    }

    private fun onTransactionClicked(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context, R.style.CustomAlertDialog)
        val viewGroup = currentView.findViewById<ViewGroup>(android.R.id.content)
        val dialogView: View =
            LayoutInflater.from(currentView.context).inflate(R.layout.dialog_box, viewGroup, false)
        builder.setView(dialogView)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()

        dialogView.btn_approve.setOnClickListener {
            val db = FirebaseDatabase.getInstance().reference.child(transactionList.get(position).transactionID)

            val map = mutableMapOf<String, Any>()
            map["status"] = Status.APPROVED
            db.updateChildren(map)
            alertDialog.dismiss()
        }

        dialogView.btn_reject.setOnClickListener {
            val db = FirebaseDatabase.getInstance().reference.child(transactionList.get(position).transactionID)

            val map = mutableMapOf<String, Any>()
            map["status"] = Status.REJECTED
            db.updateChildren(map)
            alertDialog.dismiss()
        }

        dialogView.btn_delete.setOnClickListener {
            val db = FirebaseDatabase.getInstance().reference.child(transactionList.get(position).transactionID)
            db.removeValue()
            alertDialog.dismiss()
        }
    }
}
