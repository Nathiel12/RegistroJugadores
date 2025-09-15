package edu.ucne.registrojugadores.Presentation.Partidas.List

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
import edu.ucne.registrojugadores.Domain.Model.Partida

@Composable
fun PartidaListScreen(
    onNavigateToEdit: (Int) -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToPlayers: () -> Unit,
    viewModel: ListPartidaViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    PartidaListBody(
        state = state,
        onNavigateToPlayers = onNavigateToPlayers,
        onEvent = { event ->
            when (event) {
                is ListPartidaUiEvent.Edit -> onNavigateToEdit(event.id)
                is ListPartidaUiEvent.CreateNew -> onNavigateToCreate()
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@Composable
private fun PartidaListBody(
    state: ListPartidaUiState,
    onNavigateToPlayers: () -> Unit,
    onEvent: (ListPartidaUiEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            Row {
                FloatingActionButton(onClick = { onEvent(ListPartidaUiEvent.CreateNew) }) {
                    Text("+")
                }
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(state.partidas) { partida ->
                    PartidaCard(
                        partida = partida,
                        onClick = { onEvent(ListPartidaUiEvent.Edit(partida.partidaId)) },
                        onDelete = { onEvent(ListPartidaUiEvent.Delete(partida.partidaId)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun PartidaCard(
    partida: Partida,
    onClick: (Partida) -> Unit,
    onDelete: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(partida) }
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
                    text = "Partida #${partida.partidaId}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = if (partida.esFinalizada) "Finalizada" else "En juego...",
                    color = if (partida.esFinalizada) Color.Green else Color.Gray
                )
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Jugadores: ${partida.jugador1Id} vs ${partida.jugador2Id}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "Fecha: ${partida.fecha}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(Modifier.height(8.dp))

            if (partida.esFinalizada) {
                Text(
                    text = if (partida.ganadorId != null)
                        "Ganador: ${partida.ganadorId}"
                    else
                        "Empate",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (partida.ganadorId != null) Color(0xFF0066CC) else Color.Gray
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { onDelete(partida.partidaId) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar partida")
                }
            }
        }
    }
}

@Preview
@Composable
private fun PartidaListBodyPreview() {
    MaterialTheme {
        val state = ListPartidaUiState(
            partidas = listOf(
                Partida(
                    partidaId = 1,
                    fecha = "15/01/2024",
                    jugador1Id = 101,
                    jugador2Id = 102,
                    ganadorId = 101,
                    esFinalizada = true
                ),
                Partida(
                    partidaId = 2,
                    fecha = "16/01/2024",
                    jugador1Id = 103,
                    jugador2Id = 104,
                    ganadorId = null,
                    esFinalizada = true
                ),
                Partida(
                    partidaId = 3,
                    fecha = "17/01/2024",
                    jugador1Id = 105,
                    jugador2Id = 106,
                    ganadorId = null,
                    esFinalizada = false
                )
            )
        )
        PartidaListBody(
            state = state,
            onNavigateToPlayers = {},
            onEvent = {}
        )
    }
}