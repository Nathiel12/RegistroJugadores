package edu.ucne.registrojugadores.Data.Local.Partida

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.ucne.registrojugadores.Domain.Model.Partida

@Entity(tableName = "Partidas")
class PartidaEntity (
    @PrimaryKey(autoGenerate = true )
    val partidaId: Int=0,
    val fecha: String? = "",
    val jugador1Id: Int?,
    val jugador2Id: Int?,
    val ganadorId: Int?,
    val esFinalizada: Boolean = false)
{}

