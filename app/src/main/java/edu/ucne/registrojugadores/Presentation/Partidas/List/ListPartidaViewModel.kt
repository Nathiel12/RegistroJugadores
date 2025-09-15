package edu.ucne.registrojugadores.Presentation.Partidas.List

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registrojugadores.Domain.UseCase.PartidasUseCase.DeletePartidaUseCase
import edu.ucne.registrojugadores.Domain.UseCase.PartidasUseCase.ObservePartidaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListPartidaViewModel @Inject constructor(
    private val observePartidaUseCase: ObservePartidaUseCase,
    private val deletePartidaUseCase: DeletePartidaUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ListPartidaUiState(isLoading = true))
    val state: StateFlow<ListPartidaUiState> = _state.asStateFlow()

    init {
        onEvent(ListPartidaUiEvent.Load)
    }

    fun onEvent(event: ListPartidaUiEvent){
        when(event){
            ListPartidaUiEvent.Load -> observePartida()
            is ListPartidaUiEvent.Delete -> onDelete(event.id)
            ListPartidaUiEvent.CreateNew -> _state.update { it.copy(navigateToCreate = true) }
            is ListPartidaUiEvent.Edit ->  _state.update { it.copy(navigateToEditId = event.id) }
            is ListPartidaUiEvent.ShowMessage -> _state.update { it.copy(message = event.message) }
        }
    }

    private fun observePartida(){
        viewModelScope.launch {
            observePartidaUseCase().collectLatest { partidas ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        partidas = partidas,
                        message = null
                    )
                }
            }
        }
    }

    private fun onDelete(id: Int){
        viewModelScope.launch {
            try {
                deletePartidaUseCase(id)
                onEvent(ListPartidaUiEvent.ShowMessage("Partida eliminada"))
            }catch (e: Exception){
                onEvent(ListPartidaUiEvent.ShowMessage("Error al eliminar: ${e.message}"))
            }
        }
    }

    fun onNavigationHandled(){
        _state.update {
            it.copy(
                navigateToCreate = false,
                navigateToEditId = null,
                message = null
            )
        }
    }
}