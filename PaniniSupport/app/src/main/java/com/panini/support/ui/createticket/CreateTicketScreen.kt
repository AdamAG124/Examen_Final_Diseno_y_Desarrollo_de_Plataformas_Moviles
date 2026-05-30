package com.panini.support.ui.createticket

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.panini.support.core.featureflags.FeatureFlags
import com.panini.support.core.result.UiState
import com.panini.support.di.AppViewModelFactory
import com.panini.support.domain.model.Priority
import com.panini.support.domain.model.TicketCategory
import com.panini.support.ui.common.DropdownSelector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTicketScreen(
    onCreated: () -> Unit,
    onBack: () -> Unit,
    viewModel: CreateTicketViewModel = viewModel(factory = AppViewModelFactory())
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var provider by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf(Priority.MEDIUM) }
    var category by remember { mutableStateOf(TicketCategory.INVENTORY) }

    LaunchedEffect(state) {
        if (state is UiState.Success) {
            viewModel.consume()
            onCreated()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo ticket") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Atrás") } }
            )
        }
    ) { padding ->
        Column(
            Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title, onValueChange = { title = it },
                label = { Text("Título") }, modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = description, onValueChange = { description = it },
                label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = provider, onValueChange = { provider = it },
                label = { Text("Proveedor") }, modifier = Modifier.fillMaxWidth()
            )
            DropdownSelector(
                label = "Prioridad", options = Priority.entries, selected = priority,
                optionLabel = { it.label }, onSelected = { priority = it }
            )
            DropdownSelector(
                label = "Categoría", options = TicketCategory.entries.filter {
                    FeatureFlags.SHOW_ADMIN_CATEGORIES || !it.adminOnly
                }, selected = category,
                optionLabel = { it.label }, onSelected = { category = it }
            )

            if (state is UiState.Error) {
                Text((state as UiState.Error).message, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = { viewModel.create(title, description, priority, provider, category) },
                enabled = state !is UiState.Loading,
                modifier = Modifier.fillMaxWidth()
            ) { Text("Crear ticket") }
        }
    }
}