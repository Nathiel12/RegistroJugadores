package edu.ucne.registrojugadores.Data.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registrojugadores.Data.Local.PlayerEntity
import edu.ucne.registrojugadores.Data.Local.PlayerDao

@Database(entities = [PlayerEntity::class],
    version = 1,
    exportSchema = false)

abstract class PlayerDB: RoomDatabase() {

    abstract fun playerDao(): PlayerDao
}