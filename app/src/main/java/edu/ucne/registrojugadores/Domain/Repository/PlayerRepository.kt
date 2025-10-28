package edu.ucne.registrojugadores.Domain.Repository

import edu.ucne.registrojugadores.Domain.Model.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    fun observePlayer(): Flow<List<Player>>

    suspend fun getPlayer(id: String): Player?

    suspend fun createPlayerLocal(player: Player): String

    suspend fun upsert(player: Player)

    suspend fun delete(id: String)

    suspend fun getPlayersByName(nombre: String): List<Player>

    suspend fun getAllPlayers(): List<Player>

    suspend fun postPendingPlayers(): Boolean

    suspend fun descargarJugadoresDeApi(): Boolean
}