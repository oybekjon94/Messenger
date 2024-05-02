package com.oybekdev.whatsup.app

import android.app.Application
import com.oybekdev.whatsup.di.appModule
import com.oybekdev.whatsup.di.localModule
import com.oybekdev.whatsup.di.remoteModule
import com.oybekdev.whatsup.di.repositoryModule
import com.oybekdev.whatsup.di.useCaseModule
import com.oybekdev.whatsup.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule,repositoryModule, useCaseModule, localModule, remoteModule, viewModelModule)
        }
    }
}