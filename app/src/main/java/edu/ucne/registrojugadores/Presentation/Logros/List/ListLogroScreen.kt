package edu.ucne.registrojugadores.Presentation.Logros.List

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ListLogroScreen(
    onNavigateToEdit: (Int) -> Unit,
    onNavigateToCreate: () -> Unit,
    viewModel: ListLogroViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.navigateToCreate) {
        if (state.navigateToCreate) {
            onNavigateToCreate()
            viewModel.onNavigationHandled()
        }
    }

    LaunchedEffect(state.navigateToEditId) {
        state.navigateToEditId?.let { id ->
            onNavigateToEdit(id)
            viewModel.onNavigationHandled()
        }
    }

    ListLogroBody(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun ListLogroBody(
    state: ListLogroUiState,
    onEvent: (ListLogroUiEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(ListLogroUiEvent.CreateNew) }) {
                Text("+")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            if (state.logros.isEmpty() && !state.isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "No hay logros registrados",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Presiona + para crear el primero",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.LightGray
                    )
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    state.message?.let { message ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFE3F2FD)
                            )
                        ) {
                            Text(
                                text = message,
                                modifier = Modifier.padding(16.dp),
                                color = Color(0xFF0D47A1)
                            )
                        }
                    }

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(state.logros) { logro ->
                            LogroCard(
                                logro = logro,
                                onClick = { onEvent(ListLogroUiEvent.Edit(logro.logroId)) },
                                onDelete = { onEvent(ListLogroUiEvent.Delete(logro.logroId)) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LogroCard(
    logro: edu.ucne.registrojugadores.Domain.Model.Logros.Logro,
    onClick: (edu.ucne.registrojugadores.Domain.Model.Logros.Logro) -> Unit,
    onDelete: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(logro) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = logro.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = if (logro.esLogrado) "Completado" else "Pendiente",
                    color = if (logro.esLogrado) Color.Green else Color.Gray
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = logro.descripcion,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ID: ${logro.logroId}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )

                IconButton(onClick = { onDelete(logro.logroId) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar logro")
                }
            }
        }
    }
}

@Preview
@Composable
private fun ListLogroBodyPreview() {
    MaterialTheme {
        val state = ListLogroUiState(
            logros = listOf(
                edu.ucne.registrojugadores.Domain.Model.Logros.Logro(
                    logroId = 1,
                    nombre = "Primera Victoria",
                    descripcion = "Ganar tu primera partida de Tres en Raya",
                    esLogrado = true
                ),
                edu.ucne.registrojugadores.Domain.Model.Logros.Logro(
                    logroId = 2,
                    nombre = "Victorias Consecutivas",
                    descripcion = "Ganar 5 partidas seguidas sin perder",
                    esLogrado = false
                ),
                edu.ucne.registrojugadores.Domain.Model.Logros.Logro(
                    logroId = 3,
                    nombre = "Maestro del Juego",
                    descripcion = "Ganar 50 partidas en total",
                    esLogrado = false
                )
            )
        )
        ListLogroBody(
            state = state,
            onEvent = {}
        )
    }
}