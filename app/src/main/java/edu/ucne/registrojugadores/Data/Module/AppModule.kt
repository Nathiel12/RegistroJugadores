package edu.ucne.registrojugadores.Data.Module

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import edu.ucne.registrojugadores.Data.DB.PlayerDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registrojugadores.Data.Local.Logros.LogroDAO
import edu.ucne.registrojugadores.Data.Local.Partida.PartidaDAO
import edu.ucne.registrojugadores.Data.Local.Player.PlayerDao
import edu.ucne.registrojugadores.Data.Remote.JugadorApi
import edu.ucne.registrojugadores.Data.Remote.MovimientoApi
import edu.ucne.registrojugadores.Data.Remote.PartidaApi
import edu.ucne.registrojugadores.Data.Repository.Api.JugadoresApiRepositoryImpl
import edu.ucne.registrojugadores.Data.Repository.Api.MovimientoApiRepositoryImpl
import edu.ucne.registrojugadores.Data.Repository.Api.PartidaApiRepositoryImpl
import edu.ucne.registrojugadores.Data.Repository.Logros.LogroRepositoryImpl
import edu.ucne.registrojugadores.Data.Repository.PartidaRepositoryImpl
import edu.ucne.registrojugadores.Data.Repository.PlayerRepositoryImpl
import edu.ucne.registrojugadores.Domain.Repository.Api.JugadorApiRepository
import edu.ucne.registrojugadores.Domain.Repository.Api.MovimientoApiRepository
import edu.ucne.registrojugadores.Domain.Repository.Api.PartidaApiRepository
import edu.ucne.registrojugadores.Domain.Repository.Logros.LogroRepository
import edu.ucne.registrojugadores.Domain.Repository.Partidas.PartidaRepository
import edu.ucne.registrojugadores.Domain.Repository.PlayerRepository
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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

    private const val BASE_URL = "https://gestionhuacalesapi.azurewebsites.net/"

    @Provides
    @Singleton
    fun providePlayerDao(playerDB: PlayerDB): PlayerDao {
        return playerDB.playerDao()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providePartidaApi(moshi: Moshi): PartidaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PartidaApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovimientoApi(moshi: Moshi): MovimientoApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(MovimientoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideJugadorApi(moshi: Moshi): JugadorApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(JugadorApi::class.java)
    }

    @Provides
    @Singleton
    fun providePartidaApiRepository(api: PartidaApi): PartidaApiRepository {
        return PartidaApiRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideMovimientoApiRepository(api: MovimientoApi): MovimientoApiRepository {
        return MovimientoApiRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideJugadorApiRepository(api: JugadorApi): JugadorApiRepository {
        return JugadoresApiRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providePartidaDao(playerDB: PlayerDB): PartidaDAO {
        return playerDB.partidaDao()
    }

    @Provides
    @Singleton
    fun provideLogroDao(playerDB: PlayerDB): LogroDAO {
        return playerDB.logroDao()
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
    fun provideLogroRepositoryImpl(logroDao: LogroDAO): LogroRepositoryImpl {
        return LogroRepositoryImpl(logroDao)
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

    @Provides
    @Singleton
    fun provideLogroRepository(impl: LogroRepositoryImpl): LogroRepository {
        return impl
    }
}