package edu.ucne.registrojugadores.Data.Mapper

import edu.ucne.registrojugadores.Data.Local.Partida.PartidaEntity
import edu.ucne.registrojugadores.Domain.Model.Partida

fun PartidaEntity.toPartida(): Partida {
    return Partida(
        partidaId = partidaId,
        fecha = fecha ?: "",
        jugador1Id = jugador1Id ?: 0,
        jugador2Id = jugador2Id ?: 0,
        ganadorId = ganadorId ?: 0,
        esFinalizada = esFinalizada
    )
}

fun Partida.toEntity(): PartidaEntity {
    return PartidaEntity(
        partidaId = partidaId,
        fecha = fecha,
        jugador1Id = jugador1Id,
        jugador2Id = jugador2Id,
        ganadorId = ganadorId,
        esFinalizada = esFinalizada
    )
}