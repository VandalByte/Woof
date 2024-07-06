package com.dev.woof

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // loading all the fragments
        val homeFragment = HomeFragment()
        val petsFragment = PetsFragment()
        val notificationFragment = NotificationFragment()
        val settingsFragment = SettingsFragment()
        // initial fragment
        setCurrentFragment(homeFragment)
        // getting the menu
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuHome -> setCurrentFragment(homeFragment)
                R.id.menuPets -> setCurrentFragment(petsFragment)
                R.id.menuNotifications -> setCurrentFragment(notificationFragment)
                R.id.menuSettings -> setCurrentFragment(settingsFragment)
            }
            true
        }
    }
    // function to set the current fragment
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
}