package com.cursokotlin.codechallenge.presentation.ui.viewmodels.uimodels

import com.cursokotlin.codechallenge.data.internal.adapteritems.ComicAdapterItem
import com.cursokotlin.codechallenge.utils.Event

data class CharactersDescriptionUiModel (
    val showRecyclerView: Event<ComicAdapterItem>? = null
)
