package edu.ucne.registrojugadores.Domain.UseCase.LogrosUseCase

import edu.ucne.registrojugadores.Domain.Model.Logros.Logro
import edu.ucne.registrojugadores.Domain.Repository.Logros.LogroRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveLogroUseCase @Inject constructor(
    private val repository: LogroRepository
) {
    operator fun invoke(): Flow<List<Logro>> {
        return repository.observeLogro()
    }
}