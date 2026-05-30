package com.panini.support.domain.model

enum class TicketStatus(val label: String) {
    OPEN("Abierto"),
    IN_PROGRESS("En progreso"),
    RESOLVED("Resuelto"),
    CLOSED("Cerrado");

    companion object {
        fun fromApi(value: String): TicketStatus =
            entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: OPEN
    }
}