package edu.ucne.registrojugadores.Data.Remote.Mapper

import edu.ucne.registrojugadores.Data.Remote.dto.MovimientoDto
import edu.ucne.registrojugadores.Domain.Model.Movimiento

fun MovimientoDto.toDomain(partidaId: Int): Movimiento = Movimiento(
    partidaId = partidaId,
    jugador = jugador,
    posicionFila = posicionFila,
    posicionColumna = posicionColumna,
)

fun Movimiento.toDto(): MovimientoDto = MovimientoDto(
    partidaId = partidaId,
    jugador = jugador,
    posicionFila = posicionFila,
    posicionColumna = posicionColumna
)