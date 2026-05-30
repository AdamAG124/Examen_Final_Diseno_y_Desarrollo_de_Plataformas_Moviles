package com.panini.support.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.panini.support.ui.createticket.CreateTicketViewModel
import com.panini.support.ui.login.LoginViewModel
import com.panini.support.ui.ticketdetail.TicketDetailViewModel
import com.panini.support.ui.ticketlist.TicketListViewModel

@Suppress("UNCHECKED_CAST")
class AppViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(LoginViewModel::class.java) ->
            LoginViewModel(AppContainer.authRepository) as T

        modelClass.isAssignableFrom(TicketListViewModel::class.java) ->
            TicketListViewModel(AppContainer.ticketRepository, AppContainer.eventBus) as T

        modelClass.isAssignableFrom(TicketDetailViewModel::class.java) ->
            TicketDetailViewModel(AppContainer.ticketRepository) as T

        modelClass.isAssignableFrom(CreateTicketViewModel::class.java) ->
            CreateTicketViewModel(AppContainer.ticketRepository) as T

        else -> throw IllegalArgumentException("ViewModel desconocido: ${modelClass.name}")
    }
}