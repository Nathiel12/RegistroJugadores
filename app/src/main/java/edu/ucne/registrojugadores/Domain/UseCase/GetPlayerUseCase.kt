package edu.ucne.registrojugadores.Domain.UseCase

import edu.ucne.registrojugadores.Domain.Model.Player
import edu.ucne.registrojugadores.Domain.Repository.PlayerRepository
import javax.inject.Inject

class GetPlayerUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(id: Int): Player? {
        if (id <= 0) throw IllegalArgumentException("El id debe ser mayor que 0")
        return repository.getPlayer(id)
    }
}