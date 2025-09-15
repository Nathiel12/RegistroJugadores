package edu.ucne.registrojugadores.Domain.UseCase.PartidasUseCase

import edu.ucne.registrojugadores.Domain.Model.Partida
import edu.ucne.registrojugadores.Domain.Repository.Partidas.PartidaRepository
import javax.inject.Inject

class UpsertPartidaUseCase @Inject constructor(
    private val repository: PartidaRepository
) {
    suspend operator fun invoke(partida: Partida): Result<Int> {

        if (partida.jugador1Id <= 0) {
            return Result.failure(IllegalArgumentException("Jugador 1 es requerido"))
        }

        if (partida.jugador2Id <= 0) {
            return Result.failure(IllegalArgumentException("Jugador 2 es requerido"))
        }

        if (partida.jugador1Id == partida.jugador2Id) {
            return Result.failure(IllegalArgumentException("Los jugadores no pueden ser el mismo"))
        }

        if (partida.fecha.isBlank()) {
            return Result.failure(IllegalArgumentException("La fecha es requerida"))
        }

        if (partida.esFinalizada && partida.ganadorId == null) {
            return Result.failure(IllegalArgumentException("Debe especificar un ganador para partidas finalizadas"))
        }

        return runCatching {
            repository.upsert(partida)
        }
    }
}
