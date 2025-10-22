package edu.ucne.registrojugadores.Data.Repository.Api

import edu.ucne.registrojugadores.Data.Remote.Mapper.toDomain
import edu.ucne.registrojugadores.Data.Remote.Mapper.toDto
import edu.ucne.registrojugadores.Data.Remote.MovimientoApi
import edu.ucne.registrojugadores.Domain.Model.Movimiento
import edu.ucne.registrojugadores.Domain.Repository.Api.MovimientoApiRepository
import javax.inject.Inject

class MovimientoApiRepositoryImpl @Inject constructor(
    private val movimientoApi: MovimientoApi
) : MovimientoApiRepository {

    override suspend fun getMovimientos(partidaId: Int): List<Movimiento> {
        return try {
            movimientoApi.getMovimientos(partidaId).map { it.toDomain(partidaId) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun crearMovimiento(movimiento: Movimiento): Boolean {
        return try {
            val dto = movimiento.toDto()
            movimientoApi.crearMovimiento(dto)
            true
        } catch (e: Exception) {
            false
        }
    }
}