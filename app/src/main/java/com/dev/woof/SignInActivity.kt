package com.dev.woof

//import android.annotation.SuppressLint
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInClient
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.api.ApiException
//import android.widget.TextView
//
//class MainActivity : AppCompatActivity() {private lateinit var googleSignInClient: GoogleSignInClient
//    private val RC_SIGN_IN = 100 // Request code for sign-in
//    private lateinit var userNameTextView: TextView
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//
//        }
//        userNameTextView = findViewById(R.id.userNameTextView)
//        // Configure sign-in options
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.web_client_id)) // Replace with your web client ID
//            .requestEmail()
//            .build()
//
//        // Build GoogleSignInClient
//        googleSignInClient = GoogleSignIn.getClient(this, gso)
//
//        val signInButton = findViewById<Button>(R.id.signInButton)
//        signInButton.setOnClickListener {
//            val signInIntent = googleSignInClient.signInIntent
//            startActivityForResult(signInIntent, RC_SIGN_IN)
//        }
//
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                val account = task.getResult(ApiException::class.java)
//                val userName = account?.displayName
//                userNameTextView.text = "User Name: $userName" // Set the text of the TextView
//                Log.d("MainActivity", "User signed in: $userName")
//            } catch (e: ApiException) {
//                Log.w("MainActivity", "signInResult:failed code=" + e.statusCode)
//            }
//        }
//    }
//}