package edu.ucne.registrojugadores.Data.Mapper

import edu.ucne.registrojugadores.Data.Local.Player.PlayerEntity
import edu.ucne.registrojugadores.Domain.Model.Player

fun PlayerEntity.toDomain():Player=
    Player(
        Jugadorid = Jugadorid,
        Nombres = Nombres,
        Partidas = Partidas
    )

fun Player.toEntity():PlayerEntity=
    PlayerEntity(
        Jugadorid = Jugadorid,
        Nombres = Nombres,
        Partidas = Partidas
    )