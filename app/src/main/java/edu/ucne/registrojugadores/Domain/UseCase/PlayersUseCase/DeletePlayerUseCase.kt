package edu.ucne.registrojugadores.Domain.UseCase.PlayersUseCase

import edu.ucne.registrojugadores.Domain.Repository.PlayerRepository
import javax.inject.Inject

class DeletePlayerUseCase @Inject constructor(
    private val repository: PlayerRepository
) {
    suspend operator fun invoke(id: String) {
        if (id.isBlank()) throw IllegalArgumentException("El ID debe ser mayor que 0")
        repository.delete(id)
    }
}