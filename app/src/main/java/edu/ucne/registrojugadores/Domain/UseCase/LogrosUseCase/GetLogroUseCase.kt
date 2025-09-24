package edu.ucne.registrojugadores.Domain.UseCase.LogrosUseCase

import edu.ucne.registrojugadores.Domain.Model.Logros.Logro
import edu.ucne.registrojugadores.Domain.Repository.Logros.LogroRepository
import javax.inject.Inject

class GetLogroUseCase @Inject constructor(
    private val repository: LogroRepository
) {
    suspend operator fun invoke(id: Int): Logro? {
        if (id <= 0) throw IllegalArgumentException("El id debe ser mayor que 0")
        return repository.getLogro(id)
    }
}