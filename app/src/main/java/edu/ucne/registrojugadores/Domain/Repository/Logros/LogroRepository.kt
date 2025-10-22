package edu.ucne.registrojugadores.Domain.Repository.Logros

import edu.ucne.registrojugadores.Domain.Model.Logros.Logro
import kotlinx.coroutines.flow.Flow

interface LogroRepository {

    fun observeLogro(): Flow<List<Logro>>

    suspend fun getLogro(id:Int): Logro?

    suspend fun upsert(logro: Logro):Int

    suspend fun delete(id:Int)
}