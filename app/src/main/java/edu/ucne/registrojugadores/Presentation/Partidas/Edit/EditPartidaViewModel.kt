package edu.ucne.registrojugadores.Presentation.Partidas.Edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrojugadores.Domain.Model.Partida
import edu.ucne.registrojugadores.Domain.Repository.PlayerRepository
import edu.ucne.registrojugadores.Domain.UseCase.PartidasUseCase.DeletePartidaUseCase
import edu.ucne.registrojugadores.Domain.UseCase.PartidasUseCase.GetPartidaUseCase
import edu.ucne.registrojugadores.Domain.UseCase.PartidasUseCase.UpsertPartidaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPartidaViewModel @Inject constructor(
    private val getPartidaUseCase: GetPartidaUseCase,
    private val upsertPartidaUseCase: UpsertPartidaUseCase,
    private val deletePartidaUseCase: DeletePartidaUseCase,
    private val playerRepository: PlayerRepository
) : ViewModel(){
    private val _state = MutableStateFlow(EditPartidaUiState())
    val state: StateFlow<EditPartidaUiState> = _state.asStateFlow()

    fun onEvent(event: EditPartidaUiEvent){
        when(event){
            is EditPartidaUiEvent.Load -> onLoad(event.id)
            is EditPartidaUiEvent.Save -> onSave()
            is EditPartidaUiEvent.Delete -> onDelete()
            is EditPartidaUiEvent.Jugador1Changed -> onJugador1Changed(event.value)
            is EditPartidaUiEvent.Jugador2Changed -> onJugador2Changed(event.value)
            is EditPartidaUiEvent.FechaChanged -> onFechaChanged(event.value)
            is EditPartidaUiEvent.GanadorChanged -> onGanadorChanged(event.value)
            is EditPartidaUiEvent.esFinalizadaChanged -> onFinalizadaChanged(event.value)
        }
    }

    private fun onLoad(id: Int?){
        if(id == null || id == 0){
            _state.update { it.copy(isNew = true, partidaId = null) }
            loadJugadoresDisponibles()
            return
        }
        viewModelScope.launch{
            val partida = getPartidaUseCase(id)
            if(partida != null){
                _state.update {
                    it.copy(
                        isNew = false,
                        partidaId = partida.partidaId,
                        fecha = partida.fecha,
                        jugador1Id = partida.jugador1Id,
                        jugador2Id = partida.jugador2Id,
                        ganadorId = partida.ganadorId,
                        esFinalizada = partida.esFinalizada
                    )
                }
                loadJugadoresDisponibles()
            }
        }
    }

    private fun onJugador1Changed(jugadorId: Int){
        viewModelScope.launch {
            val jugador = playerRepository.getPlayer(jugadorId)
            _state.update {
                it.copy(
                    jugador1Id = jugadorId,
                    jugador1Error = null
                )
            }
        }
    }

    private fun onJugador2Changed(jugadorId: Int){
        viewModelScope.launch {
            val jugador = playerRepository.getPlayer(jugadorId)
            _state.update {
                it.copy(
                    jugador2Id = jugadorId,
                    jugador2Error = null
                )
            }
        }
    }

    private fun onFechaChanged(fecha: String){
        _state.update {
            it.copy(
                fecha = fecha,
                fechaError = if(fecha.isBlank()) "Fecha requerida" else null
            )
        }
    }

    private fun onGanadorChanged(ganadorId: Int?){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    ganadorId = ganadorId
                )
            }
        }
    }

    private fun onFinalizadaChanged(finalizada: Boolean){
        _state.update { it.copy(esFinalizada = finalizada) }
    }

    private fun onSave(){
        viewModelScope.launch {
            if(_state.value.fecha.isBlank()){
                _state.update { it.copy(fechaError = "Fecha requerida") }
                return@launch
            }
            if(_state.value.jugador1Id == 0){
                _state.update { it.copy(jugador1Error = "Jugador 1 requerido") }
                return@launch
            }
            if(_state.value.jugador2Id == 0){
                _state.update { it.copy(jugador2Error = "Jugador 2 requerido") }
                return@launch
            }
            if(_state.value.jugador1Id == _state.value.jugador2Id){
                _state.update { it.copy(jugador1Error = "Jugadores no pueden ser iguales", jugador2Error = "Jugadores no pueden ser iguales") }
                return@launch
            }
            _state.update { it.copy(isSaving = true) }

            try{
                val partida = Partida(
                    partidaId = _state.value.partidaId ?: 0,
                    fecha = _state.value.fecha,
                    jugador1Id = _state.value.jugador1Id,
                    jugador2Id = _state.value.jugador2Id,
                    ganadorId = _state.value.ganadorId,
                    esFinalizada = _state.value.esFinalizada
                )

                upsertPartidaUseCase(partida)

                _state.update {
                    it.copy(
                        isSaving = false,
                        saved = true
                    )
                }

            }catch (e: Exception){
                _state.update {
                    it.copy(
                        isSaving = false,
                        fechaError = "Error al guardar: ${e.message}"
                    )
                }
            }
        }
    }
    private fun onDelete(){
        val id = _state.value.partidaId?: return

        viewModelScope.launch {
            _state.update { it.copy(isDeleting = true) }
            try {
                deletePartidaUseCase(id)
                _state.update { it.copy(isDeleting = false, deleted = true) }
            }catch (e: Exception){
                _state.update {
                    it.copy(
                        isDeleting = false,
                        fechaError = "Error al eliminar: ${e.message}"
                    )
                }
            }
        }
    }

    private fun loadJugadoresDisponibles(){
        viewModelScope.launch {
            val jugadores = playerRepository.getAllPlayers()
            _state.update { it.copy(jugadoresDisponibles = jugadores) }
        }
    }
}