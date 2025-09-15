package edu.ucne.registrojugadores.Presentation.Partidas.Edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registrojugadores.Domain.Model.Player

@Composable
fun EditPartidaScreen(
    partidaId: Int?,
    viewModel: EditPartidaViewModel = hiltViewModel()
) {
    LaunchedEffect(partidaId) {
        viewModel.onEvent(EditPartidaUiEvent.Load(partidaId))
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    EditPartidaBody(state, viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditPartidaBody(
    state: EditPartidaUiState,
    onEvent: (EditPartidaUiEvent) -> Unit
) {
    var showJugadorSelector by remember { mutableStateOf(false) }
    var selectorType by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState()

    if (showJugadorSelector) {
        ModalBottomSheet(
            onDismissRequest = { showJugadorSelector = false },
            sheetState = sheetState
        ) {
            JugadorSelectorBottomSheet(
                jugadores = state.jugadoresDisponibles,
                onJugadorSelected = { jugador ->
                    when (selectorType) {
                        "jugador1" -> onEvent(EditPartidaUiEvent.Jugador1Changed(jugador.Jugadorid))
                        "jugador2" -> onEvent(EditPartidaUiEvent.Jugador2Changed(jugador.Jugadorid))
                    }
                    showJugadorSelector = false
                }
            )
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedButton(
                onClick = {
                    selectorType = "jugador1"
                    showJugadorSelector = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (state.jugador1Id == 0) "Seleccionar Jugador 1"
                    else "Jugador 1: ${state.jugadoresDisponibles.find { it.Jugadorid == state.jugador1Id }?.Nombres ?: "ID ${state.jugador1Id}"}",
                    modifier = Modifier.weight(1f)
                )
            }
            if (state.jugador1Error != null) {
                Text(
                    state.jugador1Error,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    selectorType = "jugador2"
                    showJugadorSelector = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (state.jugador2Id == 0) "Seleccionar Jugador 2"
                    else "Jugador 2: ${state.jugadoresDisponibles.find { it.Jugadorid == state.jugador2Id }?.Nombres ?: "ID ${state.jugador2Id}"}",
                    modifier = Modifier.weight(1f)
                )
            }
            if (state.jugador2Error != null) {
                Text(
                    state.jugador2Error,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.fecha,
                onValueChange = { onEvent(EditPartidaUiEvent.FechaChanged(it)) },
                label = { Text("Fecha") },
                isError = state.fechaError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.fechaError != null) {
                Text(
                    state.fechaError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(16.dp))

            if (state.jugador1Id > 0 && state.jugador2Id > 0) {
                val jugador1 = state.jugadoresDisponibles.find { it.Jugadorid == state.jugador1Id }
                val jugador2 = state.jugadoresDisponibles.find { it.Jugadorid == state.jugador2Id }

                Text("Ganador:", style = MaterialTheme.typography.labelMedium)
                Spacer(Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    FilterChip(
                        selected = state.ganadorId == state.jugador1Id,
                        onClick = { onEvent(EditPartidaUiEvent.GanadorChanged(state.jugador1Id)) },
                        label = { Text(jugador1?.Nombres ?: "Jugador 1") }
                    )

                    Spacer(Modifier.width(8.dp))

                    FilterChip(
                        selected = state.ganadorId == state.jugador2Id,
                        onClick = { onEvent(EditPartidaUiEvent.GanadorChanged(state.jugador2Id)) },
                        label = { Text(jugador2?.Nombres ?: "Jugador 2") }
                    )

                    Spacer(Modifier.width(8.dp))

                    FilterChip(
                        selected = state.ganadorId == null,
                        onClick = { onEvent(EditPartidaUiEvent.GanadorChanged(null)) },
                        label = { Text("Empate") }
                    )
                }
                Spacer(Modifier.height(16.dp))
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Switch(
                    checked = state.esFinalizada,
                    onCheckedChange = { onEvent(EditPartidaUiEvent.esFinalizadaChanged(it)) }
                )
                Spacer(Modifier.width(8.dp))
                Text("Partida finalizada")
            }

            Spacer(Modifier.height(16.dp))
            
            Row {
                Button(
                    onClick = { onEvent(EditPartidaUiEvent.Save) },
                    enabled = !state.isSaving,
                    modifier = Modifier.weight(1f)
                ) { Text("Guardar") }

                Spacer(Modifier.width(8.dp))

                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { onEvent(EditPartidaUiEvent.Delete) },
                        enabled = !state.isDeleting,
                        modifier = Modifier.weight(1f)
                    ) { Text("Eliminar") }
                }
            }
        }
    }
}

@Composable
fun JugadorSelectorBottomSheet(
    jugadores: List<Player>,
    onJugadorSelected: (Player) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Seleccionar Jugador", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(jugadores) { jugador ->
                Button(
                    onClick = { onJugadorSelected(jugador) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(jugador.Nombres)
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditPartidaBodyPreview() {
    val state = EditPartidaUiState()
    MaterialTheme {
        EditPartidaBody(state = state) { _ -> }
    }
}