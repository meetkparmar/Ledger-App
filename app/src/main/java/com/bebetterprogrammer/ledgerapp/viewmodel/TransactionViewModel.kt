package com.bebetterprogrammer.ledgerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bebetterprogrammer.ledgerapp.utils.FirebaseUserLiveData

class TransactionViewModel(private val app: Application) : AndroidViewModel(app){

    val authenticationState = FirebaseUserLiveData()
}