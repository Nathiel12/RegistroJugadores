package edu.ucne.registrojugadores.Data.Module

import android.content.Context
import androidx.room.Room
import edu.ucne.registrojugadores.Data.DB.PlayerDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrojugadores.Data.Local.Partida.PartidaDAO
import edu.ucne.registrojugadores.Data.Local.Player.PlayerDao
import edu.ucne.registrojugadores.Data.Repository.PartidaRepositoryImpl
import edu.ucne.registrojugadores.Data.Repository.PlayerRepositoryImpl
import edu.ucne.registrojugadores.Domain.Repository.Partidas.PartidaRepository
import edu.ucne.registrojugadores.Domain.Repository.PlayerRepository
import javax.inject.Singleton

@InstallIn(
    SingletonComponent::class)
@Module

object AppModule {
    @Provides
    @Singleton
    fun providePlayerDB(@ApplicationContext appContext: Context): PlayerDB {
        return Room.databaseBuilder(
                appContext,
                PlayerDB::class.java,
                "Player_DB"
            ).fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun providePlayerDao(playerDB: PlayerDB): PlayerDao {
        return playerDB.playerDao()
    }

    @Provides
    @Singleton
    fun providePartidaDao(playerDB: PlayerDB): PartidaDAO {
        return playerDB.partidaDao()
    }

    @Provides
    @Singleton
    fun providePlayerRepositoryImpl(playerDao: PlayerDao): PlayerRepositoryImpl {
        return PlayerRepositoryImpl(playerDao)
    }

    @Provides
    @Singleton
    fun providePartidaRepositoryImpl(partidaDao: PartidaDAO): PartidaRepositoryImpl {
        return PartidaRepositoryImpl(partidaDao)
    }

    @Provides
    @Singleton
    fun providePlayerRepository(impl: PlayerRepositoryImpl): PlayerRepository {
        return impl
    }

    @Provides
    @Singleton
    fun providePartidaRepository(impl: PartidaRepositoryImpl): PartidaRepository {
        return impl
    }
}