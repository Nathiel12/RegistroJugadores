package edu.ucne.registrojugadores.Presentation.Players.Edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlin.jvm.Throws


@Composable
fun EditPlayerScreen(
    playerId: Int?,viewModel: EditPlayerViewModel = hiltViewModel()
) {
    LaunchedEffect(playerId) {
        viewModel.onEvent(EditPlayerUiEvent.Load(playerId))
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    EditPlayerBody(state, viewModel::onEvent)
}

@Composable
private fun EditPlayerBody(
    state: EditPlayerUiState,
    onEvent: (EditPlayerUiEvent) -> Unit
) {

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = state.nombre,
                onValueChange = { onEvent(EditPlayerUiEvent.NombreChanged(it)) },
                label = { Text("Nombre") },
                isError = state.nombreError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.nombreError != null) {
                Text(
                    state.nombreError,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = state.partida?.toString() ?: "",
                onValueChange = { onEvent(EditPlayerUiEvent.PartidaChanged(it)) },
                label = { Text("Partida") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = state.partidaError != null,
                modifier = Modifier.fillMaxWidth()
            )
            if (state.partidaError != null) Text(
                state.partidaError,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(Modifier.height(16.dp))

            Row {
                Button(
                    onClick = { onEvent(EditPlayerUiEvent.Save) },
                    enabled = !state.isSaving,
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Guardar") }
                Spacer(Modifier.fillMaxWidth())
                if (!state.isNew) {
                    OutlinedButton(
                        onClick = { onEvent(EditPlayerUiEvent.Delete) },
                        enabled = !state.isDeleting
                    ) { Text("Eliminar") }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditPlayerBodyPreview() {
    val state = EditPlayerUiState()
    MaterialTheme {
        EditPlayerBody(state = state) {}
    }
}