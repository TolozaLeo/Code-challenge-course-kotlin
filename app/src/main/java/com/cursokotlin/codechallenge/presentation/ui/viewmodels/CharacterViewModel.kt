package com.cursokotlin.codechallenge.presentation.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.codechallenge.data.Result
import com.cursokotlin.codechallenge.data.adapteritems.CharacterAdapterItem
import com.cursokotlin.codechallenge.data.adapteritems.ComicAdapterItem
import com.cursokotlin.codechallenge.data.internal.ServerError
import com.cursokotlin.codechallenge.data.internal.getGenericError
import com.cursokotlin.codechallenge.data.repository.AvengersRepository
import com.cursokotlin.codechallenge.data.response.Character
import com.cursokotlin.codechallenge.utils.Event
import com.cursokotlin.codechallenge.utils.replaceHttpForHttps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
data class CharactersUiModel (
    val showCharactersList: Event<List<CharacterAdapterItem>>? = null,
    val showError: Event<ServerError?>? = null,
)

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val avengersRepository: AvengersRepository,
): ViewModel() {
    private val _uiState = MutableLiveData<CharactersUiModel>()
    val uiState: LiveData<CharactersUiModel>
        get() = _uiState

    private var characterList = mutableListOf<CharacterAdapterItem>()

    fun getCharacters() = viewModelScope.launch(Dispatchers.IO){
        when(val result = avengersRepository.getCharacters(0)){
            is Result.Success -> {

                characterList = result.data.data.results.map { characterResponse ->
                    CharacterAdapterItem(
                        id = characterResponse.id,
                        //replace http for https is required for glide library
                        imageUrl = characterResponse.thumbnail.path.replaceHttpForHttps() +
                                ".${characterResponse.thumbnail.extension}",
                        name = characterResponse.name,
                        description = characterResponse.description,
                        comic = characterResponse.comics.items.map {
                            ComicAdapterItem(name = it.name)
                        }
                    )
                }.toMutableList()

                emitUiModel(showCharactersList = Event(characterList))
            }
            is Result.Error ->{
                emitUiModel(showError = Event(result.error))
            }
        }
    }

    private suspend fun emitUiModel(
        showCharactersList: Event<List<CharacterAdapterItem>>? = null,
        showError: Event<ServerError>? = null,
    ) = withContext(Dispatchers.Main) {
        _uiState.value = CharactersUiModel(
            showCharactersList = showCharactersList,
            showError = showError,
        )
    }
}

