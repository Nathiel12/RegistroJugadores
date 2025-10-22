package edu.ucne.registrojugadores.Domain.Repository.Api

import edu.ucne.registrojugadores.Domain.Model.Movimiento

interface MovimientoApiRepository {
    suspend fun getMovimientos(partidaId: Int): List<Movimiento>
    suspend fun crearMovimiento(movimiento: Movimiento): Boolean
}