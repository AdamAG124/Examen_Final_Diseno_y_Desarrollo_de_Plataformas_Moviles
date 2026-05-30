package com.panini.support.domain.model

enum class TicketCategory(val label: String, val adminOnly: Boolean = false) {
    INVENTORY("Inventario / Faltantes"),
    DISTRIBUTION("Distribución"),
    LOGISTICS("Logística"),
    SUPPLIER("Proveedor"),
    PACKAGING("Empaque de sobres"),
    BILLING("Facturación interna", adminOnly = true);

    companion object {
        fun fromApi(value: String): TicketCategory =
            entries.firstOrNull { it.name.equals(value, ignoreCase = true) } ?: INVENTORY
    }
}