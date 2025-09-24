package edu.ucne.registrojugadores.Domain.UseCase.LogrosUseCase

import edu.ucne.registrojugadores.Domain.Model.Logros.Logro
import edu.ucne.registrojugadores.Domain.Repository.Logros.LogroRepository
import javax.inject.Inject

class UpsertLogroUseCase @Inject constructor(
    private val repository: LogroRepository
) {
    suspend operator fun invoke(logro: Logro): Result<Int> {

        if (logro.nombre.isBlank()) {
            return Result.failure(IllegalArgumentException("El nombre es requerido"))
        }

        if (logro.descripcion.isBlank()) {
            return Result.failure(IllegalArgumentException("La descripcion es requerida"))
        }

        return runCatching {
            repository.upsert(logro)
        }
    }
}