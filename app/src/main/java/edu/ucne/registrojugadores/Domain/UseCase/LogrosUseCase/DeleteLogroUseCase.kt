package edu.ucne.registrojugadores.Domain.UseCase.LogrosUseCase

import edu.ucne.registrojugadores.Domain.Repository.Logros.LogroRepository
import javax.inject.Inject

class DeleteLogroUseCase @Inject constructor(
    private val repository: LogroRepository
) {
    suspend operator fun invoke(id: Int) {
        if (id <= 0) throw IllegalArgumentException("El ID debe ser mayor que 0")
        repository.delete(id)
    }
}
