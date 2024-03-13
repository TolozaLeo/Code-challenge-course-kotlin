package com.cursokotlin.codechallenge.presentation.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.codechallenge.data.Result
import com.cursokotlin.codechallenge.data.service.AvengersApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val api: AvengersApi,
): ViewModel() {
    fun getCharacters() = viewModelScope.launch(Dispatchers.IO){
        when(val result = api.getCharacters(0)){
            is Result.Success -> {

            }
            else ->{

            }
        }
    }

    fun getEvents() = viewModelScope.launch(Dispatchers.IO){
        api.getEvents(0)
    }
}
