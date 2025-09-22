package edu.ucne.registrojugadores.Data.Repository

import edu.ucne.registrojugadores.Data.Local.Player.PlayerDao
import edu.ucne.registrojugadores.Data.Local.Player.PlayerEntity
import edu.ucne.registrojugadores.Data.Mapper.toEntity
import edu.ucne.registrojugadores.Domain.Model.Player
import edu.ucne.registrojugadores.Domain.Repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlayerRepositoryImpl @Inject constructor(
    private val playerDao: PlayerDao
) : PlayerRepository {

    override fun observePlayer(): Flow<List<Player>> {
        return playerDao.observerAll().map { entities ->
            entities.map { it.toPlayer() }
        }
    }

    override suspend fun getPlayer(id: Int): Player? {
        return playerDao.getById(id)?.toPlayer()
    }

    override suspend fun upsert(player: Player): Int {
        val entity = player.toEntity()
        val result = playerDao.upsert(entity)
        return if (player.Jugadorid == 0) result.toInt() else player.Jugadorid
    }

    override suspend fun delete(id: Int) {
        playerDao.deleteById(id)
    }

    override suspend fun getPlayersByName(nombre: String): List<Player> {
        return playerDao.getPlayersByName(nombre).map { it.toPlayer() }
    }

    override suspend fun getAllPlayers(): List<Player> {
        return playerDao.getAllPlayers().map { it.toPlayer() }
    }
}
fun PlayerEntity.toPlayer(): Player {
    return Player(
        Jugadorid = Jugadorid,
        Nombres = Nombres,
        Partidas = Partidas
    )
}

fun Player.toEntity(): PlayerEntity {
    return PlayerEntity(
        Jugadorid = Jugadorid,
        Nombres = Nombres,
        Partidas = Partidas
    )
}
