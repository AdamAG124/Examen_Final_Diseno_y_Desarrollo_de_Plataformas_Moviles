package com.panini.support.data.repository

import com.panini.support.core.event.AppEvent
import com.panini.support.core.event.AppEventBus
import com.panini.support.data.mock.MockTicketData
import com.panini.support.domain.model.Priority
import com.panini.support.domain.model.Ticket
import com.panini.support.domain.model.TicketCategory
import com.panini.support.domain.model.TicketStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TicketRepository(
    private val eventBus: AppEventBus
) : ITicketRepository {

    private val _tickets = MutableStateFlow<List<Ticket>>(emptyList())
    override val tickets: StateFlow<List<Ticket>> = _tickets.asStateFlow()

    private var counter = 1006 // para generar IDs nuevos en la PoC

    override suspend fun refresh() {
        delay(600)
        _tickets.value = MockTicketData.seed().sortedForBoard()
    }

    override fun getTicketById(id: String): Ticket? =
        _tickets.value.firstOrNull { it.id == id }

    override suspend fun createTicket(
        title: String,
        description: String,
        priority: Priority,
        provider: String,
        category: TicketCategory
    ) {
        delay(400)
        counter += 1
        val nuevo = Ticket(
            id = "TCK-${1000 + counter}",
            title = title,
            description = description,
            priority = priority,
            status = TicketStatus.OPEN,
            provider = provider,
            category = category,
            createdAt = "2026-05-30"
        )
        _tickets.update { (it + nuevo).sortedForBoard() }
        eventBus.emit(AppEvent.TicketCreated(nuevo.title))
    }

    override suspend fun updateStatus(id: String, status: TicketStatus) {
        delay(300)
        _tickets.update { lista ->
            lista.map { if (it.id == id) it.copy(status = status) else it }
                .sortedForBoard()
        }
        eventBus.emit(AppEvent.StatusUpdated(status.label))
    }

    override suspend fun updatePriority(id: String, priority: Priority) {
        delay(300)
        _tickets.update { lista ->
            lista.map { if (it.id == id) it.copy(priority = priority) else it }
                .sortedForBoard()
        }
        eventBus.emit(AppEvent.PriorityUpdated(priority.label))
    }

    private fun List<Ticket>.sortedForBoard(): List<Ticket> =
        sortedWith(
            compareByDescending<Ticket> { it.priority.weight }
                .thenByDescending { it.createdAt }
        )
}