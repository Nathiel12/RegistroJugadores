package edu.ucne.registrojugadores.Presentation.Players.List

sealed interface ListPlayerUiEvent{
    data object Load: ListPlayerUiEvent
    data class Delete(val id: Int) : ListPlayerUiEvent
    data object CreateNew: ListPlayerUiEvent
    data class Edit(val id: Int) : ListPlayerUiEvent
    data class ShowMessage(val message: String) : ListPlayerUiEvent
}