package com.example.interviewface

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNavigation)
        setupBottomNavigation()

        // Set initial fragment
        if (savedInstanceState == null) {
            loadFragment(HomeFragment.newInstance())
        }
    }

    private fun setupBottomNavigation() {
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> loadFragment(HomeFragment.newInstance())
                R.id.navigation_interviews -> loadFragment(InterviewsFragment.newInstance())
                R.id.navigation_tips -> loadFragment(TipsFragment.newInstance())
                R.id.navigation_profile -> loadFragment(ProfileFragment.newInstance())
                R.id.navigation_more -> loadFragment(MoreFragment.newInstance())
            }
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
