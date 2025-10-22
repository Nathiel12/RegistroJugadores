package edu.ucne.registrojugadores.Data.Repository.Api

import edu.ucne.registrojugadores.Data.Remote.Mapper.toDomain
import edu.ucne.registrojugadores.Data.Remote.Mapper.toDto
import edu.ucne.registrojugadores.Data.Remote.PartidaApi
import edu.ucne.registrojugadores.Domain.Model.Partida
import edu.ucne.registrojugadores.Domain.Repository.Api.PartidaApiRepository
import javax.inject.Inject

class PartidaApiRepositoryImpl @Inject constructor(
    private val partidaApi: PartidaApi
) : PartidaApiRepository {

    override suspend fun getAllPartidas(): List<Partida> {
        return try {
            partidaApi.getAllPartidas().map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun getPartida(id: Int): Partida? {
        return try {
            partidaApi.getPartida(id).toDomain()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun crearPartida(partida: Partida): Partida? {
        return try {
            val dto = partida.toDto()
            val resultado = partidaApi.crearPartida(dto)
            resultado.toDomain()
        } catch (e: Exception) {
            null
        }
    }
}