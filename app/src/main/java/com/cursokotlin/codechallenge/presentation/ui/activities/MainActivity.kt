package com.cursokotlin.codechallenge.presentation.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.cursokotlin.codechallenge.R
import com.cursokotlin.codechallenge.databinding.ActivityMainBinding
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.MainViewModel
import com.cursokotlin.codechallenge.utils.gone
import com.cursokotlin.codechallenge.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        onItemOfBottomNavClicked()
    }

    private fun setupToolbar() {
        navController = getNavController()
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = TOP_LEVEL_DESTINATIONS,
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        handleToolbarVisibility()
    }

    private fun handleToolbarVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> shouldShowNavComponents(false)

                else -> {
                    shouldShowNavComponents(true)
                }
            }
        }
    }

    private fun shouldShowNavComponents(shouldShow: Boolean) {
        if (shouldShow) {
            binding.toolbar.visible()
            binding.bottomNavigation.visible()
        } else {
            binding.toolbar.gone()
            binding.bottomNavigation.gone()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigate(R.id.charactersFragment)
        return true
    }


    private fun onItemOfBottomNavClicked() {
        binding.bottomNavigation.setOnItemSelectedListener {
            mainViewModel.itemMenuClicked(it.itemId)
            true
        }
    }

    private fun getNavController() =
        (supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment).navController

    companion object {
        val TOP_LEVEL_DESTINATIONS = setOf(
            R.id.charactersFragment,
            R.id.eventsFragment
        )
    }
}