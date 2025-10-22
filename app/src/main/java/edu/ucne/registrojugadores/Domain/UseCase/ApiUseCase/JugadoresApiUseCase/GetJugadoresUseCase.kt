package edu.ucne.registrojugadores.Domain.UseCase.ApiUseCase.JugadoresApiUseCase

import edu.ucne.registrojugadores.Domain.Model.Player
import edu.ucne.registrojugadores.Domain.Repository.Api.JugadorApiRepository
import javax.inject.Inject

class GetJugadoresUseCase @Inject constructor(
    private val repository: JugadorApiRepository
) {
    suspend operator fun invoke(): List<Player> {
        return repository.getAllJugadores()
    }
}
