package com.cursokotlin.codechallenge.presentation.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.codechallenge.data.Result
import com.cursokotlin.codechallenge.data.internal.adapteritems.EventAdapterItem
import com.cursokotlin.codechallenge.data.internal.ServerError
import com.cursokotlin.codechallenge.data.repository.AvengersRepository
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.uimodels.EventsUiModel
import com.cursokotlin.codechallenge.utils.Event
import com.cursokotlin.codechallenge.utils.replaceHttpForHttps
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class EventsViewModel @Inject constructor(
    private val avengersRepository: AvengersRepository,
): ViewModel() {
    private val _uiState = MutableLiveData<EventsUiModel>()
    val uiState: LiveData<EventsUiModel>
        get() = _uiState

    private var eventsList = mutableListOf<EventAdapterItem>()

    fun getEvents() = viewModelScope.launch(Dispatchers.IO){
        when(val result = avengersRepository.getEvents(0)){
            is Result.Success -> {

                eventsList = result.data.data.results.map { eventsResponse ->
                    EventAdapterItem(
                        id = eventsResponse.id,
                        imageUrl = eventsResponse.thumbnail.path.replaceHttpForHttps() +
                                ".${eventsResponse.thumbnail.extension}",
                        title = eventsResponse.title,
                        description = eventsResponse.description,
                    )
                }.toMutableList()

                emitUiModel(showEventsList = Event(eventsList))
            }
            is Result.Error ->{
                emitUiModel(showError = Event(result.error))
            }
        }
    }

    private suspend fun emitUiModel(
        showEventsList: Event<List<EventAdapterItem>>? = null,
        showError: Event<ServerError>? = null,
    ) = withContext(Dispatchers.Main) {
        _uiState.value = EventsUiModel(
            showEventsList = showEventsList,
            showError = showError,
        )
    }
}
