package edu.ucne.registrojugadores.Domain.Repository.Api

import edu.ucne.registrojugadores.Domain.Model.Partida

interface PartidaApiRepository {
    suspend fun getAllPartidas(): List<Partida>
    suspend fun getPartida(id: Int): Partida?
    suspend fun crearPartida(partida: Partida): Partida?
}