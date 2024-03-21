package com.cursokotlin.codechallenge.presentation.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursokotlin.codechallenge.R
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.uimodels.MainUiModel
import com.cursokotlin.codechallenge.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState: MutableLiveData<MainUiModel> = MutableLiveData()
    val uiState: LiveData<MainUiModel>
        get() = _uiState

    fun itemMenuClicked(itemId: Int) {
        if (itemId == R.id.characters) {
            emitUiModel(navigateToCharactersFragment = Unit)
        } else {
            emitUiModel(navigateToEventsFragment = Unit)
        }
    }


    private fun emitUiModel(
        navigateToCharactersFragment: Unit? = null,
        navigateToEventsFragment: Unit? = null,
    ) {
        _uiState.value = MainUiModel(
            navigateToCharactersFragment = Event(navigateToCharactersFragment),
            navigateToEventsFragment = Event(navigateToEventsFragment),
        )
    }
}

