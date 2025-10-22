package edu.ucne.registrojugadores.Data.Remote.Mapper

import edu.ucne.registrojugadores.Data.Remote.dto.PartidaDto
import edu.ucne.registrojugadores.Domain.Model.Partida

fun PartidaDto.toDomain(): Partida = Partida(
    partidaId =  partidaId ?: 0,
    fecha = "",
    jugador1Id = jugador1Id,
    jugador2Id = jugador2Id,
    ganadorId = null,
    esFinalizada = false,
    tablero = "",
    jugadorActual = "X"
)

fun Partida.toDto(): PartidaDto = PartidaDto(
    partidaId = if (partidaId == 0) null else partidaId,
    jugador1Id = jugador1Id,
    jugador2Id = jugador2Id
)