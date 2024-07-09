/*
package com.dev.woof

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the EditText fields
        nameEditText = view.findViewById(R.id.name)
        emailEditText = view.findViewById(R.id.email)

        // Load user data from SharedPreferences
        loadUserData()
    }

    private fun loadUserData() {
        val sharedPref = activity?.getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val userName = sharedPref?.getString("user_name", "")
        val userEmail = sharedPref?.getString("user_email", "")

        nameEditText.setText(userName)
        emailEditText.setText(userEmail)
    }
}
*/
package com.dev.woof



import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var profileImageView: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the EditText fields
        nameEditText = view.findViewById(R.id.name)
        emailEditText = view.findViewById(R.id.email)
        profileImageView = view.findViewById(R.id.profileImageView)

        // Load user data from SharedPreferences
        loadUserData()
        // Load profile image from SharedPreferences
        loadProfileImage()
    }

    private fun loadUserData() {
        val sharedPref = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val userName = sharedPref.getString("user_name", "")
        val userEmail = sharedPref.getString("user_email", "")

        nameEditText.setText(userName)
        emailEditText.setText(userEmail)
    }

    private fun loadProfileImage() {
        val sharedPref = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val profilePhotoUrl = sharedPref.getString("profile_photo_url", "")

        // Load profile image using Glide (or Picasso)
        Glide.with(requireContext())
            .load(profilePhotoUrl)
            .placeholder(R.drawable.img)
            .error(R.drawable.img)
            .circleCrop()
            .into(profileImageView)
    }
}
