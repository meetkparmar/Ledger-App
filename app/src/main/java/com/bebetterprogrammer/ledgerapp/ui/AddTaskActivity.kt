package com.bebetterprogrammer.ledgerapp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bebetterprogrammer.ledgerapp.R
import com.bebetterprogrammer.ledgerapp.database.Transaction
import com.bebetterprogrammer.ledgerapp.utils.Status
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    lateinit var etName: EditText
    lateinit var etDetails: EditText
    lateinit var etAmount: EditText
    lateinit var btnSave: Button
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activty_add_task)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        database = FirebaseDatabase.getInstance().reference
        etName = findViewById(R.id.et_name)
        etDetails = findViewById(R.id.et_details)
        etAmount = findViewById(R.id.et_amount_details)
        btnSave = findViewById(R.id.btn_done)

        btnSave.setOnClickListener {
            addDetails()
            finish()
        }
    }

    private fun addDetails() {
        val name = etName.text.toString()
        val details = etDetails.text.toString()
        val amount = etAmount.text.toString()

        if (name.isNullOrEmpty() || details.isNullOrEmpty() || amount.isNullOrEmpty()) {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
        } else {
            val date: String
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val calender = Calendar.getInstance()
            date = sdf.format(calender.time)

            val id = database.push().key
            val transaction = Transaction(id!!, name, details, amount, date, Status.PENDING)
            database.child(id).setValue(transaction)

            Toast.makeText(this, "Transaction added", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}
