package edu.ucne.registrojugadores.Domain.UseCase.PartidasUseCase

import edu.ucne.registrojugadores.Domain.Model.Partida
import edu.ucne.registrojugadores.Domain.Repository.Partidas.PartidaRepository
import javax.inject.Inject

class GetPartidaUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    suspend operator fun invoke(id: Int): Partida? {
        if (id <= 0) throw IllegalArgumentException("El id debe ser mayor que 0")
        return repository.getPartida(id)
    }
}