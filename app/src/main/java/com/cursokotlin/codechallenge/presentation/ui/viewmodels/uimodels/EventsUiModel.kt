package com.cursokotlin.codechallenge.presentation.ui.viewmodels.uimodels

import com.cursokotlin.codechallenge.data.internal.adapteritems.EventAdapterItem
import com.cursokotlin.codechallenge.data.internal.ServerError
import com.cursokotlin.codechallenge.utils.Event

data class EventsUiModel (
    val showEventsList: Event<List<EventAdapterItem>>? = null,
    val showError: Event<ServerError?>? = null,
    val showLoading: Boolean = false,
)