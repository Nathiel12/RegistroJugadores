package edu.ucne.registrojugadores.Presentation.Api.PartidasApiList

sealed interface ListPartidaApiUiEvent {
    data object Load: ListPartidaApiUiEvent
    data class ShowMessage (val message: String) : ListPartidaApiUiEvent
    object NavigateToCreate : ListPartidaApiUiEvent
    data class NavigateToGame(val partidaId: Int) : ListPartidaApiUiEvent
}