package edu.ucne.registrojugadores.Domain.UseCase.PartidasUseCase

import edu.ucne.registrojugadores.Domain.Repository.Partidas.PartidaRepository
import javax.inject.Inject

class DeletePartidaUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    suspend operator fun invoke(id: Int) {
        if (id <= 0) throw IllegalArgumentException("El ID debe ser mayor que 0")
        repository.delete(id)
    }
}