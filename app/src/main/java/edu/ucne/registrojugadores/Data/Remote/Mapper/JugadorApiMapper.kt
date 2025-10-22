package edu.ucne.registrojugadores.Data.Remote.Mapper

import edu.ucne.registrojugadores.Data.Remote.dto.JugadorDto
import edu.ucne.registrojugadores.Domain.Model.Player

fun JugadorDto.toDomain(): Player = Player(
    Jugadorid = jugadorId ?: 0,
    Nombres = nombres,
    Partidas = 0
)

fun Player.toDto(): JugadorDto = JugadorDto(
    jugadorId = if (Jugadorid == 0) null else Jugadorid,
    nombres = Nombres,
    email = ""
)