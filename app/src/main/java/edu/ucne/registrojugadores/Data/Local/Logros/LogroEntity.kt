package edu.ucne.registrojugadores.Data.Local.Logros

import android.R
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Logros")
class LogroEntity (
    @PrimaryKey(autoGenerate = true )
    val logroId: Int=0,
    val nombre: String = "",
    val descripcion: String = "",
    val esLogrado: Boolean = false
)

{}
