package com.cursokotlin.codechallenge.presentation.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.uimodels.NavigationUiModel
import com.cursokotlin.codechallenge.utils.Event

class NavigationViewModel: ViewModel() {
    private val _uiState = MutableLiveData<NavigationUiModel>()
    val uiState: LiveData<NavigationUiModel>
        get() = _uiState

    fun changeToolBarTitle(title: String){
        emitUiModel(changeToolbarTitle = Event(title))
    }

    fun showBottomNavigation(shouldShow: Boolean) {
        emitUiModel(showBottomNavigation = Event(shouldShow))
    }


    private fun emitUiModel(
        changeToolbarTitle: Event<String>? = null,
        showBottomNavigation: Event<Boolean>? = null
    ){
        _uiState.value = NavigationUiModel(
            changeToolbarTitle = changeToolbarTitle,
            showBottomNavigation = showBottomNavigation,
        )
    }
}