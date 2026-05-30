package com.panini.support.data.mock

import com.panini.support.domain.model.Priority
import com.panini.support.domain.model.Ticket
import com.panini.support.domain.model.TicketCategory
import com.panini.support.domain.model.TicketStatus

object MockTicketData {

    fun seed(): List<Ticket> = listOf(
        Ticket(
            id = "TCK-1001",
            title = "Faltante de sobres en lote del proveedor MX-Norte",
            description = "El lote 4471 llegó con 1.200 sobres faltantes respecto a la orden de compra. " +
                    "Afecta la reposición de 3 puntos de venta en Guadalajara.",
            priority = Priority.CRITICAL,
            status = TicketStatus.OPEN,
            provider = "Distribuidora MX-Norte",
            category = TicketCategory.INVENTORY,
            createdAt = "2026-05-28"
        ),
        Ticket(
            id = "TCK-1002",
            title = "Retraso en distribución a puntos de venta zona Caribe",
            description = "El transportista reporta demora de 48h por aduana. Riesgo de quiebre de stock " +
                    "para el lanzamiento de la colección de figuras especiales.",
            priority = Priority.HIGH,
            status = TicketStatus.IN_PROGRESS,
            provider = "LogiCaribe S.A.",
            category = TicketCategory.DISTRIBUTION,
            createdAt = "2026-05-27"
        ),
        Ticket(
            id = "TCK-1003",
            title = "Diferencia de inventario en bodega central",
            description = "Conteo físico vs. sistema arroja diferencia de 340 cajas de álbumes. " +
                    "Se requiere auditoría de movimientos de la última semana.",
            priority = Priority.MEDIUM,
            status = TicketStatus.OPEN,
            provider = "Bodega Central CR",
            category = TicketCategory.LOGISTICS,
            createdAt = "2026-05-26"
        ),
        Ticket(
            id = "TCK-1004",
            title = "Proveedor de impresión reporta error de troquelado",
            description = "Stickers de la serie 'Estadios' salieron mal troquelados en el 8% del tiraje. " +
                    "Pendiente decisión de reimpresión.",
            priority = Priority.HIGH,
            status = TicketStatus.OPEN,
            provider = "PrintPack Internacional",
            category = TicketCategory.SUPPLIER,
            createdAt = "2026-05-25"
        ),
        Ticket(
            id = "TCK-1005",
            title = "Sobres con sellado defectuoso en empaque",
            description = "Reporte de minorista: 1 de cada 20 sobres llega abierto. Posible falla en la " +
                    "selladora de la línea 2.",
            priority = Priority.MEDIUM,
            status = TicketStatus.RESOLVED,
            provider = "Empaques del Valle",
            category = TicketCategory.PACKAGING,
            createdAt = "2026-05-24"
        ),
        Ticket(
            id = "TCK-1006",
            title = "Reposición urgente punto de venta San José",
            description = "Tienda agotó stock de álbumes base. Demanda alta por inicio del Mundial. " +
                    "Necesita reposición express.",
            priority = Priority.LOW,
            status = TicketStatus.CLOSED,
            provider = "Retail SJO-01",
            category = TicketCategory.DISTRIBUTION,
            createdAt = "2026-05-22"
        )
    )
}