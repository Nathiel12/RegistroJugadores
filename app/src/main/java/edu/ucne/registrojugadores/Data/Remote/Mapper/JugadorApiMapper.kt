package edu.ucne.registrojugadores.Data.Remote.Mapper

import edu.ucne.registrojugadores.Data.Remote.dto.JugadorRequest
import edu.ucne.registrojugadores.Data.Remote.dto.JugadorResponse
import edu.ucne.registrojugadores.Domain.Model.Player
import java.util.UUID

fun JugadorResponse.toDomain(): Player = Player(
    id = UUID.randomUUID().toString(),
    remoteId = jugadorId,
    Nombres = nombres,
    Partidas = 0
)

fun Player.toRequest(): JugadorRequest = JugadorRequest(
    nombres = Nombres,
    email = ""
)