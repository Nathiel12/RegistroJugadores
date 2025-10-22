package edu.ucne.registrojugadores.Presentation.Logros.List

import edu.ucne.registrojugadores.Domain.Model.Logros.Logro

data class ListLogroUiState(
    val isLoading: Boolean = false,
    val logros: List<Logro> = emptyList(),
    val message: String? = null,
    val navigateToCreate: Boolean = false,
    val navigateToEditId: Int? = null
)