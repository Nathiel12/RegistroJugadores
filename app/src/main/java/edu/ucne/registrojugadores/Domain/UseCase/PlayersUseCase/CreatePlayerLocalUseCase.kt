package edu.ucne.registrojugadores.Domain.UseCase.PlayersUseCase

import edu.ucne.registrojugadores.Domain.Model.Player
import edu.ucne.registrojugadores.Domain.Repository.PlayerRepository
import javax.inject.Inject

class CreatePlayerLocalUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(jugador: Player): String {
        return repository.createPlayerLocal(jugador)
    }
}