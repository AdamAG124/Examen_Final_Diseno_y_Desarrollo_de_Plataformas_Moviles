package com.panini.support.core.event

sealed interface AppEvent {
    data class TicketCreated(val title: String) : AppEvent
    data class PriorityUpdated(val title: String) : AppEvent
    data class StatusUpdated(val title: String) : AppEvent
}