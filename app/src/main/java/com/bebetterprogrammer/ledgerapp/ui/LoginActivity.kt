package com.bebetterprogrammer.ledgerapp.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bebetterprogrammer.ledgerapp.R
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LoginActivity"
        const val SIGN_IN_REQUEST_CODE = 1230
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        FirebaseAuth.getInstance().currentUser?.let {
            Log.i(TAG, "User is already logged in with name - ${FirebaseAuth.getInstance().currentUser?.displayName}!")
            openMainActivity()
        }

        setupViewAndClickListeners()
    }

    override fun onStart() {
        super.onStart()
    }

    private fun openMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun setupViewAndClickListeners() {
        signInButton.setOnClickListener {
            val providers = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
            val intent = AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build()
            startActivityForResult(intent, SIGN_IN_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                Log.i(TAG, "Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.displayName}!")
                openMainActivity()
            } else {
                Log.i(TAG, "Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }
}