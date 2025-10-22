package edu.ucne.registrojugadores.Domain.UseCase.ApiUseCase.PartidasApiUseCase

import edu.ucne.registrojugadores.Domain.Model.Partida
import edu.ucne.registrojugadores.Domain.Repository.Api.PartidaApiRepository
import javax.inject.Inject

class PostPartidasApiUseCase @Inject constructor(
    private val repository: PartidaApiRepository
) {
    suspend operator fun invoke(partida: Partida): Partida? {
        return repository.crearPartida(partida)
    }
}