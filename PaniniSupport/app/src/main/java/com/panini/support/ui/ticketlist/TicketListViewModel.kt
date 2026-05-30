package com.panini.support.ui.ticketlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panini.support.core.event.AppEvent
import com.panini.support.core.result.UiState
import com.panini.support.data.repository.ITicketRepository
import com.panini.support.domain.model.Ticket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.panini.support.core.event.AppEventBus
import kotlinx.coroutines.flow.SharedFlow

class TicketListViewModel(
    private val repository: ITicketRepository,
    eventBus: AppEventBus
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Ticket>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Ticket>>> = _uiState.asStateFlow()
    val events: SharedFlow<AppEvent> = eventBus.events

    init {
        load()
        observeTickets()
    }

    private fun load() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            runCatching { repository.refresh() }
                .onFailure { _uiState.value = UiState.Error(it.message ?: "Error al cargar tickets") }
        }
    }

    private fun observeTickets() {
        viewModelScope.launch {
            repository.tickets.collect { lista ->
                if (lista.isNotEmpty() || _uiState.value !is UiState.Loading) {
                    _uiState.value = UiState.Success(lista)
                }
            }
        }
    }
}