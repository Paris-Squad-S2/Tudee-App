package com.example.tudeeapp

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tudeeapp.di.dataModule
import com.example.tudeeapp.di.roomModule
import com.example.tudeeapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class TudeeApplication : Application() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TudeeApplication)
            modules(dataModule,roomModule,viewModelModule )
        }
    }
}