package com.cursokotlin.codechallenge.presentation.ui.viewmodels.uimodels

import com.cursokotlin.codechallenge.data.internal.adapteritems.CharacterAdapterItem
import com.cursokotlin.codechallenge.data.internal.ServerError
import com.cursokotlin.codechallenge.utils.Event

data class CharactersUiModel (
    val showCharactersList: Event<List<CharacterAdapterItem>>? = null,
    val showError: Event<ServerError?>? = null,
    val showLoading: Event<Boolean?>? =  null,
)
