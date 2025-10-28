package edu.ucne.registrojugadores.Presentation.Players.Edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrojugadores.Domain.Model.Player
import edu.ucne.registrojugadores.Domain.Repository.PlayerRepository
import edu.ucne.registrojugadores.Domain.UseCase.PlayersUseCase.CreatePlayerLocalUseCase
import edu.ucne.registrojugadores.Domain.UseCase.PlayersUseCase.DeletePlayerUseCase
import edu.ucne.registrojugadores.Domain.UseCase.PlayersUseCase.GetPlayerUseCase
import edu.ucne.registrojugadores.Domain.UseCase.PlayersUseCase.TriggerSyncUseCase
import edu.ucne.registrojugadores.Domain.UseCase.PlayersUseCase.UpsertPlayerUseCase
import edu.ucne.registrojugadores.Domain.UseCase.PlayersUseCase.ValidatePlayerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class EditPlayerViewModel @Inject constructor(
    private val getPlayerUseCase: GetPlayerUseCase,
    private val upsertPlayerUseCase: UpsertPlayerUseCase,
    private val createPlayerLocalUseCase: CreatePlayerLocalUseCase,
    private val deletePlayerUseCase: DeletePlayerUseCase,
    private val validatePlayerUseCase: ValidatePlayerUseCase,
    private val triggerSyncUseCase: TriggerSyncUseCase
) : ViewModel(){
    private val _state = MutableStateFlow(EditPlayerUiState())
    val state: StateFlow<EditPlayerUiState> = _state.asStateFlow()
    private var currentPlayerId: String? = null
    private var currentPlayerRemoteId: Int? = null

    private fun validateNombre(nombre: String) {
        viewModelScope.launch {
            val result = validatePlayerUseCase(
                nombre = nombre,
                partida = _state.value.partida,
                currentPlayerId = currentPlayerId
            )

            _state.update {
                it.copy(nombreError = result.nombreError)
            }
        }
    }
    private fun validatePartida(partida: Int?) {
        viewModelScope.launch {
            val result = validatePlayerUseCase(
                nombre = _state.value.nombre,
                partida = partida,
                currentPlayerId = currentPlayerId
            )

            _state.update {
                it.copy(partidaError = result.partidaError)
            }
        }
    }

    fun onEvent(event: EditPlayerUiEvent){
        when(event){
            is EditPlayerUiEvent.Load -> onLoad(event.id)
            is EditPlayerUiEvent.NombreChanged ->{
                _state.update {
                    it.copy(
                        nombre = event.value,
                        nombreError = null
                    )
                }
                validateNombre(event.value)
            }
            is EditPlayerUiEvent.PartidaChanged ->{
                val partidaInt = event.value.toIntOrNull()
                _state.update {
                    it.copy(
                        partida = partidaInt,
                        partidaError = null
                    )
                }
                validatePartida(partidaInt)
            }
            EditPlayerUiEvent.Save -> onSave()
            EditPlayerUiEvent.Delete -> onDelete()
        }
    }
    private fun onLoad(id: String?) {
        if (id == null) {
            _state.update { it.copy(isNew = true, playerId = null) }
            return
        }
        viewModelScope.launch {
            val player = getPlayerUseCase(id)
            if (player != null) {
                currentPlayerId = player.id
                currentPlayerRemoteId = player.remoteId
                _state.update {
                    it.copy(
                        isNew = false,
                        nombre = player.Nombres,
                        partida = player.Partidas
                    )
                }
            }
        }
    }
    private fun onSave() {
        viewModelScope.launch {
            val partidaInt = state.value.partida ?: return@launch
            val validationResult = validatePlayerUseCase(
                nombre = _state.value.nombre,
                partida = _state.value.partida,
                currentPlayerId = currentPlayerId
            )

            if (!validationResult.isValid) {
                _state.update {
                    it.copy(
                        nombreError = validationResult.nombreError,
                        partidaError = validationResult.partidaError,
                        isSaving = false
                    )
                }
                return@launch
            }

            _state.update { it.copy(isSaving = true) }
            try {
                val player = Player(
                    id = currentPlayerId ?: UUID.randomUUID().toString(),
                    remoteId = if (_state.value.isNew) null else currentPlayerRemoteId,
                    Nombres = _state.value.nombre,
                    Partidas = partidaInt
                )

                if (_state.value.isNew) {
                    createPlayerLocalUseCase(player)
                    //triggerSyncUseCase()
                } else {
                    upsertPlayerUseCase(player)
                }
                _state.update {
                    it.copy(
                        isSaving = false,
                        saved = true
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isSaving = false,
                        nombreError = "Error al guardar: ${e.message}"
                    )
                }
            }
        }
    }
    private fun onDelete() {
        val id = currentPlayerId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            try {
                deletePlayerUseCase(id)
                _state.update { it.copy(isDeleting = false, deleted = true) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isDeleting = false,
                        nombreError = "Error al eliminar: ${e.message}"
                    )
                }
            }
        }
    }
}