package edu.ucne.registrojugadores.Data.Remote

import edu.ucne.registrojugadores.Data.Remote.dto.PartidaDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PartidaApi {

    @GET("api/Partidas")
    suspend fun getAllPartidas(): List<PartidaDto>

    @GET("api/Partidas/{id}")
    suspend fun getPartida(@Path("id") id: Int): PartidaDto

    @POST("api/Partidas")
    suspend fun crearPartida(@Body partida: PartidaDto): PartidaDto

}