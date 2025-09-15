package edu.ucne.registrojugadores.Domain.UseCase.PlayersUseCase

import edu.ucne.registrojugadores.Domain.Model.Player
import edu.ucne.registrojugadores.Domain.Repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObservePlayersUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    operator fun invoke(): Flow<List<Player>> {
        return repository.observePlayer()
    }
}