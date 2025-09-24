package edu.ucne.registrojugadores.Data.Local.Logros

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registrojugadores.Data.Local.Logros.LogroEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LogroDAO {

    @Query("SELECT*FROM logros ORDER BY logroId DESC")
    fun observerAll(): Flow<List<LogroEntity>>

    @Query("SELECT*FROM logros WHERE logroId=:id")
    suspend fun getById(id:Int): LogroEntity?

    @Upsert
    suspend fun upsert(logro: LogroEntity):Long

    @Delete
    suspend fun delete(entity: LogroEntity)

    @Query("DELETE FROM logros WHERE logroId=:id")
    suspend fun deleteById(id:Int)
}