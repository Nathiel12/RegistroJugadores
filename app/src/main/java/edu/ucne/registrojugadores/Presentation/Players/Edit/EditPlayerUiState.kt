package edu.ucne.registrojugadores.Presentation.Players.Edit

data class EditPlayerUiState(
    val playerId: Int? = null,
    val nombre: String = "",
    val partida: Int? = null,
    val nombreError: String? = null,
    val partidaError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false
)

