package edu.ucne.registrojugadores.Data.Local.Player

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registrojugadores.Data.Local.Player.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Query("SELECT*FROM Jugadores ORDER BY id DESC")
    fun observerAll(): Flow<List<PlayerEntity>>

    @Query("SELECT*FROM Jugadores WHERE id=:id")
    suspend fun getById(id:String): PlayerEntity?

    @Upsert
    suspend fun upsert(player: PlayerEntity):Long

    @Delete
    suspend fun delete(entity: PlayerEntity)

    @Query("DELETE FROM Jugadores WHERE id=:id")
    suspend fun deleteById(id:String)

    @Query("SELECT * FROM Jugadores WHERE Nombres = :nombre")
    suspend fun getPlayersByName(nombre: String): List<PlayerEntity>

    @Query("SELECT * FROM Jugadores ORDER BY Nombres ASC")
    suspend fun getAllPlayers(): List<PlayerEntity>

    @Query("SELECT * FROM Jugadores WHERE isPendingCreate = 1")
    suspend fun getPendingCreateJugadores(): List<PlayerEntity>

    @Query("SELECT * FROM Jugadores WHERE remoteId = :remoteId")
    suspend fun getByRemoteId(remoteId: Int): PlayerEntity?
}