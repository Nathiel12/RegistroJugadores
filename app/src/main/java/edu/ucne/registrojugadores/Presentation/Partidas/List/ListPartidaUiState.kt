package edu.ucne.registrojugadores.Presentation.Partidas.List

import edu.ucne.registrojugadores.Domain.Model.Partida

data class ListPartidaUiState(
    val isLoading: Boolean = false,
    val partidas: List<Partida> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)

