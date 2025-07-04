package com.example.tudeeapp

import android.app.Application
import com.example.tudeeapp.di.dataModule
import com.example.tudeeapp.di.roomModule
import com.example.tudeeapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class TudeeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TudeeApplication)
            modules(dataModule, roomModule, viewModelModule)
        }
    }
}