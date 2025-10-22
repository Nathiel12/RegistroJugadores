package edu.ucne.registrojugadores.Domain.UseCase.ApiUseCase.PartidasApiUseCase

import edu.ucne.registrojugadores.Domain.Model.Partida
import edu.ucne.registrojugadores.Domain.Repository.Api.PartidaApiRepository
import javax.inject.Inject

class GetPartidasApiUseCase @Inject constructor(
    private val repository: PartidaApiRepository
) {
    suspend operator fun invoke(id: Int): Partida? {
        return repository.getPartida(id)
    }
}