package com.example.abschlussprojekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.abschlussprojekt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
      supportActionBar?.hide() // Titelleiste ausblenden

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController


        binding.bottomNavBar.visibility = View.VISIBLE
        binding.bottomNavBar.setupWithNavController(navController)

    }

    // Diese Funktion versteckt die Bottom Navigation Bar
    fun hideBottomNavigation() {
        binding.bottomNavBar.visibility = View.GONE
    }

    // Diese Funktion zeigt die Bottom Navigation Bar an
    fun showBottomNavigation() {
        binding.bottomNavBar.visibility = View.VISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}