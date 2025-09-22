package edu.ucne.registrojugadores.Data.Repository

import edu.ucne.registrojugadores.Data.Local.Partida.PartidaDAO
import edu.ucne.registrojugadores.Data.Local.Partida.PartidaEntity
import edu.ucne.registrojugadores.Data.Mapper.toEntity
import edu.ucne.registrojugadores.Data.Mapper.toPartida
import edu.ucne.registrojugadores.Domain.Model.Partida
import edu.ucne.registrojugadores.Domain.Model.Player
import edu.ucne.registrojugadores.Domain.Repository.Partidas.PartidaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.collections.map

class PartidaRepositoryImpl @Inject constructor(
    private val partidaDao: PartidaDAO
) : PartidaRepository{

    override fun observePartida(): Flow<List<Partida>> {
        return partidaDao.observerAll().map { entities ->
            entities.map { it.toPartida() }
        }
    }

    override suspend fun getPartida(id: Int): Partida? {
        return partidaDao.getById(id)?.toPartida()
    }

    override suspend fun upsert(partida: Partida): Int {
        val entity = partida.toEntity()
        val result = partidaDao.upsert(entity)
        return if (partida.partidaId == 0) result.toInt() else partida.partidaId
    }

    override suspend fun delete(id: Int) {
        partidaDao.deleteById(id)
    }
}


