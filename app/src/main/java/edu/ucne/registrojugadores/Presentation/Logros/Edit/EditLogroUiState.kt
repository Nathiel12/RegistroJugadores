package edu.ucne.registrojugadores.Presentation.Logros.Edit

import edu.ucne.registrojugadores.Domain.Model.Logros.Logro

data class EditLogroUiState(
    val logroId: Int? = null,
    val nombre: String = "",
    val descripcion: String = "",
    val esLogrado: Boolean = false,
    val nombreError: String? = null,
    val descripcionError: String? = null,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val isNew: Boolean = true,
    val saved: Boolean = false,
    val deleted: Boolean = false
)