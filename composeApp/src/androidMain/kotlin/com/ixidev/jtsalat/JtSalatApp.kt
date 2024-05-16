package com.ixidev.jtsalat

import android.app.Application
import com.ixidev.jtsalat.di.koinModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class JtSalatApp : Application() {


    override fun onCreate() {
        super.onCreate()
        startKoin {
             androidLogger()
            androidContext(this@JtSalatApp)
            modules(koinModules)
        }
    }
}