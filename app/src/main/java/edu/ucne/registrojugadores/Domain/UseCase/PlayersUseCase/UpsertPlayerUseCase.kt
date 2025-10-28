package edu.ucne.registrojugadores.Domain.UseCase.PlayersUseCase

import edu.ucne.registrojugadores.Domain.Model.Player
import edu.ucne.registrojugadores.Domain.Repository.PlayerRepository
import javax.inject.Inject

class UpsertPlayerUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(player: Player) {
        if (player.Nombres.isBlank()) {
            throw IllegalArgumentException("El nombre no puede estar vacío")
        }

        if (player.Nombres.length > 50) {
            throw IllegalArgumentException("El nombre no puede tener más de 50 caracteres")
        }

        if (player.Partidas < 0) {
            throw IllegalArgumentException("Las partidas no pueden ser negativas")
        }

        repository.upsert(player)
    }
}