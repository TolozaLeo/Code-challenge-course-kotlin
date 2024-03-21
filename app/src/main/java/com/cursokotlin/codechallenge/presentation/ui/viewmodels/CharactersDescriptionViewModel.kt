package com.cursokotlin.codechallenge.presentation.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursokotlin.codechallenge.data.internal.adapteritems.CharacterAdapterItem
import com.cursokotlin.codechallenge.data.internal.adapteritems.ComicAdapterHeaderItem
import com.cursokotlin.codechallenge.data.internal.adapteritems.ComicAdapterItem
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.uimodels.CharactersDescriptionUiModel
import com.cursokotlin.codechallenge.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersDescriptionViewModel @Inject constructor() : ViewModel() {

    private val _uiState: MutableLiveData<CharactersDescriptionUiModel> = MutableLiveData()
    val uiState: LiveData<CharactersDescriptionUiModel>
        get() = _uiState


    fun setupView(characterData: CharacterAdapterItem) {
        val comicHeader = ComicAdapterHeaderItem(characterData.description, characterData.imageUrl)
        val comicsList = characterData.comic
        val characterDescription = ComicAdapterItem(comicHeader, comicsList)

        emitUiModel(showRecyclerView = Event(characterDescription))
    }

    private fun emitUiModel(
        showRecyclerView: Event<ComicAdapterItem>? = null,
    ) {
        _uiState.value = CharactersDescriptionUiModel(
            showRecyclerView = showRecyclerView,
        )
    }
}
