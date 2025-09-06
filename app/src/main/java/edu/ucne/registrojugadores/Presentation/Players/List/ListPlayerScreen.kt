package edu.ucne.registrojugadores.Presentation.Players.List

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import edu.ucne.registrojugadores.Presentation.Players.Edit.EditPlayerUiState

@Composable
fun PlayerListScreen(
    onNavigateToEdit: (Int) -> Unit,     
    onNavigateToCreate: () -> Unit,
    viewModel: ListPlayerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    PlayerListBody(
        state = state,
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
    onEvent: (ListPlayerUiEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(ListPlayerUiEvent.CreateNew) }) {
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(state.players) { player ->
                    PlayerCard(
                        player = player,
                        onClick = { onEvent(ListPlayerUiEvent.Edit(player.Jugadorid)) },
                        onDelete = { onEvent(ListPlayerUiEvent.Delete(player.Jugadorid)) }
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
    onDelete: (Int) -> Unit,
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
            IconButton(onClick = { onDelete(player.Jugadorid) }) {
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
                Player(Jugadorid = 1, Nombres = "Juan Pérez", Partidas = 25),
                Player(Jugadorid = 2, Nombres = "María García", Partidas = 42)
            )
        )
        PlayerListBody(state) {}
    }
}