package com.cursokotlin.codechallenge.presentation.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.codechallenge.data.Result
import com.cursokotlin.codechallenge.data.internal.ServerError
import com.cursokotlin.codechallenge.data.internal.adapteritems.CharacterAdapterItem
import com.cursokotlin.codechallenge.data.internal.adapteritems.ComicItem
import com.cursokotlin.codechallenge.data.repository.AvengersRepository
import com.cursokotlin.codechallenge.data.response.Character
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.uimodels.CharactersUiModel
import com.cursokotlin.codechallenge.utils.Event
import com.cursokotlin.codechallenge.utils.findBetweenParenthesis
import com.cursokotlin.codechallenge.utils.replaceHttpForHttps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val avengersRepository: AvengersRepository,
) : ViewModel() {
    private val _uiState = MutableLiveData<CharactersUiModel>()
    val uiState: LiveData<CharactersUiModel>
        get() = _uiState

    private var characterList = mutableListOf<CharacterAdapterItem>()

    fun getCharacters(page : Int = 0) = viewModelScope.launch(Dispatchers.IO) {

        avengersRepository.getCharacters(page).collect { result ->
            when (result) {
                is Result.Success -> {
                    val lastList = result.data.data.results.map { characterResponse ->
                        CharacterAdapterItem(
                            id = characterResponse.id,
                            //replace http for https is necessary for glide library
                            imageUrl = characterResponse.thumbnail.path.replaceHttpForHttps() +
                                    ".${characterResponse.thumbnail.extension}",
                            name = characterResponse.name,
                            description = characterResponse.description,
                            comic = characterResponse.extractComicsList()
                        )
                    }

                    characterList.addAll(lastList)

                    if(page == 0) emitUiModel(showCharactersList = Event(characterList.toList()))
                    else {
                        emitUiModel(refreshCharactersList = Event(characterList.toList()))
                    }
                }

                is Result.Error -> {
                    emitUiModel(showError = Event(result.error))
                }

                is Result.Loading -> {
                    emitUiModel(showLoading = result.isLoading)
                }
            }
        }
    }

    private suspend fun emitUiModel(
        showCharactersList: Event<List<CharacterAdapterItem>>? = null,
        refreshCharactersList: Event<List<CharacterAdapterItem>>? = null,
        showError: Event<ServerError>? = null,
        showLoading: Boolean = false,
    ) = withContext(Dispatchers.Main) {
        _uiState.value = CharactersUiModel(
            showCharactersList = showCharactersList,
            refreshCharactersList = refreshCharactersList,
            showError = showError,
            showLoading = showLoading,
        )
    }
}

private fun Character.extractComicsList(): List<ComicItem> {
    return this.comics.items.map {
        ComicItem(name = it.name, year = it.name.findBetweenParenthesis())
    }
}

