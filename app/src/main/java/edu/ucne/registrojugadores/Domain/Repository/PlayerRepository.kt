package edu.ucne.registrojugadores.Domain.Repository

import edu.ucne.registrojugadores.Domain.Model.Player
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    fun observePlayer(): Flow<List<Player>>

    suspend fun getPlayer(id:Int): Player?

    suspend fun upsert(player: Player):Int

    suspend fun delete(id:Int)

    suspend fun getPlayersByName(nombre: String): List<Player>

    suspend fun getAllPlayers(): List<Player>
}