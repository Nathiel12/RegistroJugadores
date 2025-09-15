package edu.ucne.registrojugadores.Domain.UseCase.PlayersUseCase

import edu.ucne.registrojugadores.Domain.Model.Player
import edu.ucne.registrojugadores.Domain.Repository.PlayerRepository
import javax.inject.Inject

class UpsertPlayerUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(player: Player): Result<Int> {

        if (player.Nombres.isBlank()) {
            return Result.failure(IllegalArgumentException("El nombre no puede estar vacío"))
        }

        if (player.Nombres.length>50){
            return Result.failure(IllegalArgumentException("El nombre no puede tener más de 50 caracteres"))
        }

        if (player.Partidas < 0) {
            return Result.failure(IllegalArgumentException("Las partidas no pueden ser negativas"))
        }

        return runCatching {
            repository.upsert(player)
        }
    }
}