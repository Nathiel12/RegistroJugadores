package edu.ucne.registrojugadores.Data.Remote.dto

data class PartidaDto(
    val partidaId: Int? = null,
    val jugador1Id: Int,
    val jugador2Id: Int
)