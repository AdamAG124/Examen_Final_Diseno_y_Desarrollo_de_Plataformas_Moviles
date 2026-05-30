package com.panini.support.domain.model

data class Ticket(
    val id: String,
    val title: String,
    val description: String,
    val priority: Priority,
    val status: TicketStatus,
    val provider: String,
    val category: TicketCategory,
    val createdAt: String
)