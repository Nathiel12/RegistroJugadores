// Data/Mapper.kt
package edu.ucne.registrojugadores.Data.Mapper

import edu.ucne.registrojugadores.Data.Local.Player.PlayerEntity
import edu.ucne.registrojugadores.Data.Remote.dto.JugadorRequest
import edu.ucne.registrojugadores.Data.Remote.dto.JugadorResponse
import edu.ucne.registrojugadores.Domain.Model.Player
import java.util.UUID

fun PlayerEntity.toDomain(): Player = Player(
    id = id,
    remoteId = remoteId,
    Nombres = Nombres,
    Partidas = Partidas
)

fun Player.toEntity(): PlayerEntity = PlayerEntity(
    id = id,
    remoteId = remoteId,
    Nombres = Nombres,
    Partidas = Partidas,
    isPendingCreate = false
)

fun JugadorResponse.toEntity(): PlayerEntity = PlayerEntity(
    id = UUID.randomUUID().toString(),
    remoteId = jugadorId,
    Nombres = nombres,
    Partidas = 0
)

fun JugadorResponse.toDomain(): Player = Player(
    id = UUID.randomUUID().toString(),
    remoteId = jugadorId,
    Nombres = nombres,
    Partidas = 0
)

fun PlayerEntity.toRequest(): JugadorRequest = JugadorRequest(
    nombres = this.Nombres,
    email = ""
)

fun Player.toRequest(): JugadorRequest = JugadorRequest(
    nombres = Nombres,
    email = ""
)