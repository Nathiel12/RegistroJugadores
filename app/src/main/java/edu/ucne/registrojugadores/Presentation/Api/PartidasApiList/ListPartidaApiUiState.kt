package edu.ucne.registrojugadores.Presentation.Api.PartidasApiList

import edu.ucne.registrojugadores.Domain.Model.Partida

data class ListPartidaApiUiState(
    val isLoading: Boolean = false,
    val partidas: List<Partida> = emptyList(),
    val message: String? = null,
    val navigateToGame: Int? = null
)

