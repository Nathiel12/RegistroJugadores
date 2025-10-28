package edu.ucne.registrojugadores.Presentation.Players.List

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete // Icono para partidas
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrojugadores.Domain.Model.Player

@Composable
fun PlayerListScreen(
    onNavigateToEdit: (String) -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToPartidas: () -> Unit,
    viewModel: ListPlayerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    PlayerListBody(
        state = state,
        onNavigateToPartidas = onNavigateToPartidas,
        onEvent = { event ->
            when (event) {
                is ListPlayerUiEvent.Edit -> onNavigateToEdit(event.id)
                is ListPlayerUiEvent.CreateNew -> onNavigateToCreate()
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@Composable
private fun PlayerListBody(
    state: ListPlayerUiState,
    onNavigateToPartidas: () -> Unit,
    onEvent: (ListPlayerUiEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    onClick = { onEvent(ListPlayerUiEvent.DownloadFromApi) },
                    modifier = Modifier.size(40.dp)
                ) {
                    Text("G", style = MaterialTheme.typography.bodySmall)
                }

                Spacer(modifier = Modifier.height(8.dp))

                FloatingActionButton(
                    onClick = { onEvent(ListPlayerUiEvent.SyncPending) },
                    modifier = Modifier.size(40.dp)
                ) {
                    Text("P", style = MaterialTheme.typography.bodySmall)
                }

                Spacer(modifier = Modifier.height(8.dp))
                FloatingActionButton(onClick = { onEvent(ListPlayerUiEvent.CreateNew) }) {
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
                items(state.players) { player ->
                    PlayerCard(
                        player = player,
                        onClick = { onEvent(ListPlayerUiEvent.Edit(player.id)) },
                        onDelete = { onEvent(ListPlayerUiEvent.Delete(player.id)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun PlayerCard(
    player: Player,
    onClick: (Player) -> Unit,
    onDelete: (String) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(player) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(player.Nombres, style = MaterialTheme.typography.titleMedium)
                Text("Partidas: ${player.Partidas}")
            }
            IconButton(onClick = { onDelete(player.id) }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
            }
        }
    }
}

@Preview
@Composable
private fun PlayerListBodyPreview() {
    MaterialTheme {
        val state = ListPlayerUiState(
            players = listOf(
                Player(id = "1", Nombres = "Juan Pérez", Partidas = 25),
                Player(id = "2", Nombres = "María García", Partidas = 42)
            )
        )
        PlayerListBody(
            state = state,
            onNavigateToPartidas = {},
            onEvent = {}
        )
    }
}