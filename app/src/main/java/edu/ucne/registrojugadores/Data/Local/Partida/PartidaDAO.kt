package edu.ucne.registrojugadores.Data.Local.Partida

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registrojugadores.Domain.Model.Partida
import kotlinx.coroutines.flow.Flow

@Dao
interface PartidaDAO {

    @Query("SELECT*FROM partidas ORDER BY partidaId DESC")
    fun observerAll(): Flow<List<PartidaEntity>>

    @Query("SELECT*FROM partidas WHERE partidaId=:id")
    suspend fun getById(id:Int): PartidaEntity?

    @Upsert
    suspend fun upsert(partida: PartidaEntity):Long

    @Delete
    suspend fun delete(entity: PartidaEntity)

    @Query("DELETE FROM partidas WHERE partidaId=:id")
    suspend fun deleteById(id:Int)

    @Query("SELECT * FROM partidas WHERE esFinalizada = 0") // false = en curso
    fun getPartidasEnCurso(): Flow<List<Partida>>

}