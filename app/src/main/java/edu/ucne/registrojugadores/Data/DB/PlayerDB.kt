package edu.ucne.registrojugadores.Data.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registrojugadores.Data.Local.Logros.LogroDAO
import edu.ucne.registrojugadores.Data.Local.Logros.LogroEntity
import edu.ucne.registrojugadores.Data.Local.Partida.PartidaDAO
import edu.ucne.registrojugadores.Data.Local.Partida.PartidaEntity
import edu.ucne.registrojugadores.Data.Local.Player.PlayerEntity
import edu.ucne.registrojugadores.Data.Local.Player.PlayerDao

@Database(entities = [PlayerEntity::class, PartidaEntity::class, LogroEntity::class],
    version = 4,
    exportSchema = false)

abstract class PlayerDB: RoomDatabase() {

    abstract fun playerDao(): PlayerDao
    abstract fun partidaDao(): PartidaDAO
    abstract fun logroDao(): LogroDAO
}