package com.panini.support.di

import com.panini.support.core.event.AppEventBus
import com.panini.support.data.repository.AuthRepository
import com.panini.support.data.repository.IAuthRepository
import com.panini.support.data.repository.ITicketRepository
import com.panini.support.data.repository.TicketRepository

object AppContainer {

    val eventBus: AppEventBus by lazy { AppEventBus() }
    val ticketRepository: ITicketRepository by lazy { TicketRepository() }
    val authRepository: IAuthRepository by lazy { AuthRepository() }
}