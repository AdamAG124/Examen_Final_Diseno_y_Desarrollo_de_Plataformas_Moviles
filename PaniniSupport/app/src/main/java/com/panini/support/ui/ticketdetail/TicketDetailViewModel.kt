package com.panini.support.ui.ticketdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panini.support.core.result.UiState
import com.panini.support.data.repository.ITicketRepository
import com.panini.support.domain.model.Priority
import com.panini.support.domain.model.Ticket
import com.panini.support.domain.model.TicketStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TicketDetailViewModel(
    private val repository: ITicketRepository
) : ViewModel() {

    private var ticketId: String? = null

    private val _uiState = MutableStateFlow<UiState<Ticket>>(UiState.Loading)
    val uiState: StateFlow<UiState<Ticket>> = _uiState.asStateFlow()

    fun bind(id: String) {
        ticketId = id
        observe(id)
    }

    private fun observe(id: String) {
        viewModelScope.launch {
            repository.tickets.collect { lista ->
                val ticket = lista.firstOrNull { it.id == id }
                _uiState.value =
                    if (ticket != null) UiState.Success(ticket)
                    else UiState.Error("Ticket no encontrado")
            }
        }
    }

    fun updateStatus(status: TicketStatus) {
        val id = ticketId ?: return
        viewModelScope.launch { repository.updateStatus(id, status) }
    }

    fun updatePriority(priority: Priority) {
        val id = ticketId ?: return
        viewModelScope.launch { repository.updatePriority(id, priority) }
    }
}