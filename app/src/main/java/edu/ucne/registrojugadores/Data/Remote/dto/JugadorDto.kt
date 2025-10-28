package edu.ucne.registrojugadores.Data.Remote.dto

data class JugadorResponse(
    val jugadorId: Int? = null,
    val nombres: String,
    val email: String
)

data class JugadorRequest(
    val nombres: String,
    val email: String
)