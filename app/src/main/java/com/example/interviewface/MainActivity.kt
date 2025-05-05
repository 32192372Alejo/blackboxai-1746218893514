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
            val fragment = when (item.itemId) {
                R.id.navigation_home -> HomeFragment.newInstance()
                R.id.navigation_interviews -> InterviewsFragment.newInstance()
                R.id.navigation_tips -> TipsFragment.newInstance()
                R.id.navigation_profile -> ProfileFragment.newInstance()
                R.id.navigation_more -> MoreFragment.newInstance()
                else -> HomeFragment.newInstance()
            }
            loadFragment(fragment)
            true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
