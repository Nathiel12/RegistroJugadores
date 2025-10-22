package edu.ucne.registrojugadores.Data.Repository.Api

import edu.ucne.registrojugadores.Data.Remote.JugadorApi
import edu.ucne.registrojugadores.Data.Remote.Mapper.toDomain
import edu.ucne.registrojugadores.Data.Remote.Mapper.toDto
import edu.ucne.registrojugadores.Domain.Model.Player
import edu.ucne.registrojugadores.Domain.Repository.Api.JugadorApiRepository
import javax.inject.Inject

class JugadoresApiRepositoryImpl @Inject constructor(
    private val jugadorApi: JugadorApi
) : JugadorApiRepository {
    override suspend fun getAllJugadores(): List<Player> {
        return try {
            jugadorApi.getAllJugadores().map { it.toDomain() }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getJugador(id: Int): Player? {
        return try {
            jugadorApi.getJugador(id).toDomain()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun crearJugador(jugador: Player): Player? {
        return try {
            val jugadorDto = jugador.toDto()
            jugadorApi.crearJugador(jugadorDto).toDomain()
        } catch (e: Exception) {
            null
        }
    }
}