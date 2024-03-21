package com.cursokotlin.codechallenge.presentation.ui.viewmodels.uimodels

import com.cursokotlin.codechallenge.utils.Event

data class NavigationUiModel (
    val changeToolbarTitle: Event<String>? = null,
    val showBottomNavigation: Event<Boolean>? = null,
)
