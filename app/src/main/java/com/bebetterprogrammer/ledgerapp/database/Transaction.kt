package com.bebetterprogrammer.ledgerapp.database

import com.bebetterprogrammer.ledgerapp.utils.Status

data class Transaction(
    val transactionID: String = "",
    val userName: String = "",
    val details: String = "",
    val amount: String = "0",
    val date: String = "",
    val status: Status = Status.PENDING
)