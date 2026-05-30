package com.panini.support.ui.ticketlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.panini.support.core.result.UiState
import com.panini.support.di.AppViewModelFactory
import com.panini.support.domain.model.Ticket
import com.panini.support.ui.common.ErrorView
import com.panini.support.ui.common.LoadingView
import com.panini.support.ui.common.PriorityChip
import com.panini.support.ui.common.StatusChip
import androidx.compose.runtime.remember
import com.panini.support.core.event.AppEvent
import com.panini.support.core.featureflags.FeatureFlags


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketListScreen(
    onTicketClick: (String) -> Unit,
    onCreateClick: () -> Unit,
    viewModel: TicketListViewModel = viewModel(factory = AppViewModelFactory())
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            val msg = when (event) {
                is AppEvent.TicketCreated -> "Ticket creado: ${event.title}"
                is AppEvent.PriorityUpdated -> "Prioridad actualizada a ${event.title}"
                is AppEvent.StatusUpdated -> "Estado actualizado a ${event.title}"
            }
            snackbarHostState.showSnackbar(msg)
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Tickets de soporte") }) },
        floatingActionButton = {
            if (FeatureFlags.CREATE_TICKET_ENABLED) {
                FloatingActionButton(onClick = onCreateClick) { Text("+") }
            }
        }
    ) { padding ->
        when (val s = state) {
            is UiState.Loading -> LoadingView()
            is UiState.Error -> ErrorView(s.message)
            is UiState.Success -> LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(s.data, key = { it.id }) { ticket ->
                    TicketCard(ticket, onClick = { onTicketClick(ticket.id) })
                }
            }
        }
    }
}

@Composable
private fun TicketCard(ticket: Ticket, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(ticket.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                PriorityChip(ticket.priority)
                StatusChip(ticket.status)
            }
            Text("Proveedor: ${ticket.provider}", style = MaterialTheme.typography.bodySmall)
            Text("Categoría: ${ticket.category.label}", style = MaterialTheme.typography.bodySmall)
            Text("Creado: ${ticket.createdAt}", style = MaterialTheme.typography.bodySmall)
        }
    }
}