package edu.ucne.registrojugadores.Domain.UseCase.ApiUseCase.MovimientosUseCase

import edu.ucne.registrojugadores.Domain.Model.Movimiento
import edu.ucne.registrojugadores.Domain.Repository.Api.MovimientoApiRepository
import javax.inject.Inject

class PostMovimientosUseCase @Inject constructor(
    private val repository: MovimientoApiRepository
) {
    suspend operator fun invoke(movimiento: Movimiento): Boolean {
        return repository.crearMovimiento(movimiento)
    }
}