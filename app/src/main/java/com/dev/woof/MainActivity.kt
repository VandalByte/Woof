package com.dev.woof

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import java.security.SecureRandom
import android.util.Base64

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nonce = generateNonce() // Generate a 16-byte nonce

        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(true)
            .setServerClientId(getString(R.string.web_client_id))
            .setAutoSelectEnabled(true)
            .setNonce(nonce)
            .build()
    }

    private fun generateNonce(): String {
        val length = 16
        val random = SecureRandom()
        val nonceBytes = ByteArray(length)
        random.nextBytes(nonceBytes)
        return Base64.encodeToString(nonceBytes, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
    }
}
