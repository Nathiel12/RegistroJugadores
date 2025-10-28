package edu.ucne.registrojugadores.Domain.UseCase.PlayersUseCase

import edu.ucne.registrojugadores.Domain.Repository.PlayerRepository
import javax.inject.Inject

class DownloadPlayersUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(): Boolean {
        return repository.descargarJugadoresDeApi()
    }
}