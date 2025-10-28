package edu.ucne.registrojugadores.Data.Remote

import edu.ucne.registrojugadores.Data.Remote.dto.MovimientoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MovimientoApi {
    @GET("api/Movimientos/{partidaId}")
    suspend fun getMovimientos(@Path("partidaId") partidaId: Int): List<MovimientoDto>

    @POST("api/Movimientos")
    suspend fun crearMovimiento(@Body movimiento: MovimientoDto)
}