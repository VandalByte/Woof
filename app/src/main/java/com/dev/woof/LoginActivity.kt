package com.dev.woof

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException


class LoginActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 1001 // request code
    }

    private lateinit var googleBtn: Button
    private lateinit var gsc: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        googleBtn = findViewById(R.id.googleBtn)
        googleBtn.setOnClickListener {
            signInWithGoogle()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.google_web_client_id))
            .build()
        gsc = GoogleSignIn.getClient(this, gso)

        // Check if user is already signed in
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            // User is already signed in, navigate to MainActivity
            val userName = account.displayName
            Toast.makeText(this, "Welcome back to Woof, $userName", Toast.LENGTH_SHORT).show()
            navigateToMainActivity()
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = gsc.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish() // Finish LoginActivity to prevent user from returning here
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign-In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                // Getting user info
                val userName = account?.displayName
                val userEmail = account?.email
                Toast.makeText(this, "Welcome to Woof, $userName", Toast.LENGTH_SHORT).show()

                // Navigate to MainActivity
                navigateToMainActivity()

            } catch (e: ApiException) {
                // Google Sign-In failed
                Toast.makeText(this, "Google Sign-In failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
