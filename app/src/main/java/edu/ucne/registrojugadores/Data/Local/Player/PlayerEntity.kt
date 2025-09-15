package edu.ucne.registrojugadores.Data.Local.Player

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Jugadores")
class PlayerEntity (
    @PrimaryKey(autoGenerate = true )
    val Jugadorid: Int=0,
    val Nombres: String,
    val Partidas: Int)
{}