package edu.ucne.registrojugadores.Domain.Model

import java.util.UUID

class Player(
    val id: String = UUID.randomUUID().toString(),
    val remoteId: Int? = null,
    val Nombres: String,
    val Partidas: Int,
    val isPendingCreate: Boolean = false
){
}
