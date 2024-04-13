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
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.NavigationViewModel
import com.cursokotlin.codechallenge.utils.gone
import com.cursokotlin.codechallenge.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()
    private val navigationViewModel: NavigationViewModel by viewModels()

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()
        setupToolbar()
        onItemOfBottomNavClicked()
    }

    private fun setupObservers() {
        navigationViewModel.uiState.observe(this){
            it.changeToolbarTitle?.getContentIfNotHandled()?.let { title ->
                changeToolbarTitle(title)
            }
        }

        mainViewModel.uiState.observe(this){
            it.navigateToCharactersFragment?.getContentIfNotHandled()?.let {
                navigateToCharactersFragment()
            }
            it.navigateToEventsFragment?.getContentIfNotHandled()?.let {
                navigateToEventsFragment()
            }
        }
    }

    private fun navigateToEventsFragment() {
        navController.navigate(R.id.eventsFragment)
    }

    private fun navigateToCharactersFragment() {
        navController.navigate(R.id.charactersFragment)
    }

    private fun setupToolbar() {
        navController = getNavController()
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = TOP_LEVEL_DESTINATIONS,
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.itemIconTintList = null
        handleNavComponentsVisibility()
    }

    private fun handleNavComponentsVisibility() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> {
                    hideToolbar()
                    hideBottomNavigation()
                }

                R.id.charactersDescriptionFragment ->{
                    showToolbar()
                    hideBottomNavigation()
                    binding.toolbar.setNavigationIcon(R.drawable.icon_cancel_back)
                }

                else -> {
                    showToolbar()
                    changeToolbarTitle(getString(R.string.toolbar_title))
                    showBottomNavigation()
                }
            }
        }
    }

    private fun changeToolbarTitle(title: String) {
        binding.toolbar.title = title
    }

    private fun hideBottomNavigation() {
        binding.bottomNavigation.gone()
    }

    private fun showBottomNavigation(){
        binding.bottomNavigation.visible()
    }

    private fun hideToolbar(){
        binding.toolbar.gone()
    }
    private fun showToolbar(){
        binding.toolbar.visible()
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