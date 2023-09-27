package com.example.abschlussprojekt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController


        binding.bottomNavBar.visibility = View.VISIBLE
        binding.bottomNavBar.setupWithNavController(navController)
        // Zurück-Button in der Toolbar anzeigen
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Listener, um den Titel automatisch zu aktualisieren
        navController.addOnDestinationChangedListener { _, destination, _ ->
            setTitle(destination.label)
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Hier die Aktion ausführen, um zur vorherigen Seite zu navigieren
                navController.navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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