package edu.ucne.registrojugadores.Presentation.Players.Edit

sealed interface EditPlayerUiEvent {
    data class Load(val id: Int?) : EditPlayerUiEvent
    data class NombreChanged(val value: String) : EditPlayerUiEvent
    data class PartidaChanged(val value: String) : EditPlayerUiEvent
    data object Save : EditPlayerUiEvent
    data object Delete : EditPlayerUiEvent
}