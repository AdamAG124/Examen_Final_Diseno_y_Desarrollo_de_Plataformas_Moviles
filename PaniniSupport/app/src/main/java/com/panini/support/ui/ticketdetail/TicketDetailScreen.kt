package com.panini.support.ui.ticketdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.panini.support.core.featureflags.FeatureFlags
import com.panini.support.core.result.UiState
import com.panini.support.di.AppViewModelFactory
import com.panini.support.domain.model.Priority
import com.panini.support.domain.model.TicketStatus
import com.panini.support.ui.common.DropdownSelector
import com.panini.support.ui.common.ErrorView
import com.panini.support.ui.common.LoadingView
import com.panini.support.ui.common.PriorityChip
import com.panini.support.ui.common.StatusChip

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketDetailScreen(
    ticketId: String,
    onBack: () -> Unit,
    viewModel: TicketDetailViewModel = viewModel(factory = AppViewModelFactory())
) {
    LaunchedEffect(ticketId) { viewModel.bind(ticketId) }
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del ticket") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Atrás") } }
            )
        }
    ) { padding ->
        when (val s = state) {
            is UiState.Loading -> LoadingView()
            is UiState.Error -> ErrorView(s.message)
            is UiState.Success -> {
                val ticket = s.data
                Column(
                    Modifier.fillMaxSize().padding(padding).padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(ticket.title, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                    Text("ID: ${ticket.id}", style = MaterialTheme.typography.bodySmall)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        PriorityChip(ticket.priority)
                        StatusChip(ticket.status)
                    }
                    Text("Proveedor: ${ticket.provider}")
                    Text("Categoría: ${ticket.category.label}")
                    Text("Creado: ${ticket.createdAt}")
                    Text(ticket.description, style = MaterialTheme.typography.bodyMedium)

                    HorizontalDivider()

                    DropdownSelector(
                        label = "Cambiar estado",
                        options = TicketStatus.entries,
                        selected = ticket.status,
                        optionLabel = { it.label },
                        onSelected = { viewModel.updateStatus(it) }
                    )

                    DropdownSelector(
                        label = "Cambiar prioridad",
                        options = Priority.entries,
                        selected = ticket.priority,
                        optionLabel = { it.label },
                        onSelected = { viewModel.updatePriority(it) },
                        enabled = FeatureFlags.PRIORITY_UPDATE_ENABLED
                    )
                }
            }
        }
    }
}