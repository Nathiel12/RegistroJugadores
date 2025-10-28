package edu.ucne.registrojugadores.Domain.UseCase.ApiUseCase.MovimientosUseCase

import edu.ucne.registrojugadores.Domain.Model.Movimiento
import edu.ucne.registrojugadores.Domain.Repository.Api.MovimientoApiRepository
import javax.inject.Inject

class GetMovimientosUseCase @Inject constructor(
    private val repository: MovimientoApiRepository
) {
    suspend operator fun invoke(partidaId: Int): List<Movimiento> {
        return repository.getMovimientos(partidaId)
    }
}