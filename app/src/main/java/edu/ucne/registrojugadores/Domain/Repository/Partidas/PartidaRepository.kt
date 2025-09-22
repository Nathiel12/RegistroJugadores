package edu.ucne.registrojugadores.Domain.Repository.Partidas

import edu.ucne.registrojugadores.Domain.Model.Partida
import kotlinx.coroutines.flow.Flow

interface PartidaRepository {

    fun observePartida(): Flow<List<Partida>>

    suspend fun getPartida(id:Int): Partida?

    suspend fun upsert(partida: Partida):Int

    suspend fun delete(id:Int)
}