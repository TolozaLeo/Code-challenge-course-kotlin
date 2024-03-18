package com.cursokotlin.codechallenge.presentation.ui.viewmodels.uimodels

import com.cursokotlin.codechallenge.utils.Event

data class MainUiModel(
    val navigateToCharactersFragment: Event<Unit?>? = null,
    val navigateToEventsFragment: Event<Unit?>? = null,
)