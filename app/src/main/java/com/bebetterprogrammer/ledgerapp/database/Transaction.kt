package com.bebetterprogrammer.ledgerapp.database

data class Transaction(
    val transactionID: String = "",
    val userName : String = "",
    val details : String = "",
    val amount: String = "0",
    val date : String = ""
)