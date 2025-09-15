package edu.ucne.registrojugadores.Presentation.Partidas.List

sealed interface ListPartidaUiEvent {
    data object Load: ListPartidaUiEvent
    data class Delete(val id: Int) : ListPartidaUiEvent
    data object CreateNew: ListPartidaUiEvent
    data class Edit(val id: Int) : ListPartidaUiEvent
    data class ShowMessage (val message: String) : ListPartidaUiEvent
}