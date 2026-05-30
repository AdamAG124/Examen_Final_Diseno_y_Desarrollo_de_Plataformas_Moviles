package com.panini.support.domain.model

enum class Priority(val label: String, val weight: Int) {
    CRITICAL("Crítica", 4),
    HIGH("Alta", 3),
    MEDIUM("Media", 2),
    LOW("Baja", 1);

    companion object {
        fun fromApi(value: String): Priority =
            entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: MEDIUM
    }
}
