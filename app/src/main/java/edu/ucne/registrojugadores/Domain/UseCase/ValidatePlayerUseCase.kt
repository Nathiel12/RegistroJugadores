package edu.ucne.registrojugadores.Domain.UseCase

import edu.ucne.registrojugadores.Domain.Repository.PlayerRepository
import javax.inject.Inject

class ValidatePlayerUseCase @Inject constructor(
    private val playerRepository: PlayerRepository
) {
    data class ValidationResult(
        val isValid: Boolean,
        val nombreError: String? = null,
        val partidaError: String? = null
    )

    suspend operator fun invoke(
        nombre: String,
        partida: Int?,
        currentPlayerId: Int? = null
    ): ValidationResult {
        val nombreError = when {
            nombre.isBlank() -> "El nombre es requerido"
            else -> {
                val existingPlayers = playerRepository.getPlayersByName(nombre)
                val isDuplicate = if (currentPlayerId != null) {
                    existingPlayers.any { it.Jugadorid != currentPlayerId }
                } else {
                    existingPlayers.isNotEmpty()
                }
                if (isDuplicate) "Nombre ya existe" else null
            }
        }

        val partidaError = when {
            partida == null -> "La cantidad de partidas es requerida"
            else -> null
        }

        return ValidationResult(
            isValid = nombreError == null && partidaError == null,
            nombreError = nombreError,
            partidaError = partidaError
        )
    }
}
