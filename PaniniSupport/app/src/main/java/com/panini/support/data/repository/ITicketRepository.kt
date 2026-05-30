package com.panini.support.data.repository

import com.panini.support.domain.model.Priority
import com.panini.support.domain.model.Ticket
import com.panini.support.domain.model.TicketCategory
import com.panini.support.domain.model.TicketStatus
import kotlinx.coroutines.flow.StateFlow

interface ITicketRepository {

    val tickets: StateFlow<List<Ticket>>

    suspend fun refresh()

    fun getTicketById(id: String): Ticket?

    suspend fun createTicket(
        title: String,
        description: String,
        priority: Priority,
        provider: String,
        category: TicketCategory
    )

    suspend fun updateStatus(id: String, status: TicketStatus)

    suspend fun updatePriority(id: String, priority: Priority)
}