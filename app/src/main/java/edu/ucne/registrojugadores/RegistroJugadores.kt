package edu.ucne.registrojugadores

import android.app.Application
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import edu.ucne.registrojugadores.Data.Workers.MyWorkerFactory
import javax.inject.Inject

@HiltAndroidApp
class RegistroJugadores : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: MyWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}