package com.dev.woof

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val requestExactAlarmPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (!isGranted) {
            // Handle the case where the permission is not granted
            Toast.makeText(this, "Exact alarm permission is required for reminders.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check for exact alarm permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SCHEDULE_EXACT_ALARM) != PackageManager.PERMISSION_GRANTED) {
                requestExactAlarmPermissionLauncher.launch(Manifest.permission.SCHEDULE_EXACT_ALARM)
            }
        }

        // Create Notification Channel
        createNotificationChannel()
        // Initialize the label TextView
        val fragmentNameTextView: TextView = findViewById(R.id.fragmentNameTextView)
        val profileImageView: ImageView = findViewById(R.id.profileImageView)
        // loading all the fragments
        val homeFragment = HomeFragment()
        val petsFragment = PetsFragment()
        val notificationFragment = NotificationFragment()
        val settingsFragment = SettingsFragment()

        // initial fragment
        setCurrentFragment(homeFragment)
        fragmentNameTextView.text = getString(R.string.menu_home)
        // getting the menu
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuHome -> {
                    setCurrentFragment(homeFragment)
                    fragmentNameTextView.text = getString(R.string.menu_home)
                }
                R.id.menuPets -> {
                    setCurrentFragment(petsFragment)
                    fragmentNameTextView.text = getString(R.string.menu_pets)
                }
                R.id.menuNotifications -> {
                    setCurrentFragment(notificationFragment)
                    fragmentNameTextView.text = getString(R.string.menu_notifications)
                }
                R.id.menuSettings -> {
                    setCurrentFragment(settingsFragment)
                    fragmentNameTextView.text = getString(R.string.menu_settings)
                }
            }
            true
        }
        // Handle click on profileImageView to navigate to SettingsFragment
        profileImageView.setOnClickListener {
            setCurrentFragment(settingsFragment)
            fragmentNameTextView.text = getString(R.string.menu_settings)
            // Optionally, you can handle other UI changes or actions here
        }
        settingsFragment.setOnProfilePhotoChangedListener(object : SettingsFragment.OnProfilePhotoChangedListener {
            override fun onProfilePhotoChanged(photoUrl: String?) {
                Glide.with(this@MainActivity)
                    .load(photoUrl)
                    .placeholder(R.drawable.img)
                    .error(R.drawable.img)
                    .circleCrop()
                    .into(profileImageView)
            }
        })
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "ReminderChannel"
            val descriptionText = "Channel for Reminder Notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("REMINDER_CHANNEL", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

}
