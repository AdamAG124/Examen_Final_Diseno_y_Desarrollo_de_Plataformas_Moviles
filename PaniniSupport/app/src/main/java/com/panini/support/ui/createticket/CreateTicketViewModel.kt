package com.panini.support.ui.createticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.panini.support.core.result.UiState
import com.panini.support.data.repository.ITicketRepository
import com.panini.support.domain.model.Priority
import com.panini.support.domain.model.TicketCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateTicketViewModel(
    private val repository: ITicketRepository
) : ViewModel() {

    private val _state = MutableStateFlow<UiState<Unit>?>(null)
    val state: StateFlow<UiState<Unit>?> = _state.asStateFlow()

    fun create(
        title: String,
        description: String,
        priority: Priority,
        provider: String,
        category: TicketCategory
    ) {
        if (title.isBlank() || provider.isBlank()) {
            _state.value = UiState.Error("Título y proveedor son obligatorios.")
            return
        }
        viewModelScope.launch {
            _state.value = UiState.Loading
            runCatching { repository.createTicket(title, description, priority, provider, category) }
                .onSuccess { _state.value = UiState.Success(Unit) }
                .onFailure { _state.value = UiState.Error(it.message ?: "Error al crear ticket") }
        }
    }

    fun consume() { _state.value = null }
}