package com.panini.support.ui.navigation

object Routes {
    const val LOGIN = "login"
    const val TICKET_LIST = "tickets"
    const val CREATE_TICKET = "tickets/create"

    const val ARG_TICKET_ID = "ticketId"
    const val TICKET_DETAIL = "tickets/{$ARG_TICKET_ID}"
    fun ticketDetail(id: String) = "tickets/$id"
}