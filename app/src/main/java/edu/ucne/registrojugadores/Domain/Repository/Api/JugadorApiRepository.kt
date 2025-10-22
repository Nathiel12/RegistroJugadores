package edu.ucne.registrojugadores.Domain.Repository.Api

import edu.ucne.registrojugadores.Domain.Model.Player

interface JugadorApiRepository {
    suspend fun getAllJugadores(): List<Player>
    suspend fun getJugador(id: Int): Player?
    suspend fun crearJugador(jugador: Player): Player?
}