package edu.ucne.registrojugadores.Presentation.Players.List

import edu.ucne.registrojugadores.Domain.Model.Player

data class ListPlayerUiState(
    val isLoading: Boolean = false,
    val players: List<Player> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)

