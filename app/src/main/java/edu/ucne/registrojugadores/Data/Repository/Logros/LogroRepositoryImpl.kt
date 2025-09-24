package edu.ucne.registrojugadores.Data.Repository.Logros

import edu.ucne.registrojugadores.Data.Local.Logros.LogroDAO
import edu.ucne.registrojugadores.Data.Mapper.toEntity
import edu.ucne.registrojugadores.Data.Mapper.toLogro
import edu.ucne.registrojugadores.Domain.Model.Logros.Logro
import edu.ucne.registrojugadores.Domain.Repository.Logros.LogroRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class LogroRepositoryImpl @Inject constructor(
    private val LogroDao: LogroDAO
) : LogroRepository {

    override fun observeLogro(): Flow<List<Logro>> {
        return LogroDao.observerAll().map { entities ->
            entities.map { it.toLogro() }
        }
    }

    override suspend fun getLogro(id: Int): Logro? {
        return LogroDao.getById(id)?.toLogro()
    }

    override suspend fun upsert(logro: Logro): Int {
        val entity = logro.toEntity()
        val result = LogroDao.upsert(entity)
        return if (logro.logroId == 0) result.toInt() else logro.logroId
    }

    override suspend fun delete(id: Int) {
        LogroDao.deleteById(id)
    }

}