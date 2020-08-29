package com.valentinhinov.ganandroidtest

import android.app.Application
import com.valentinhinov.ganandroidtest.di.allModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BreakingBadApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // use AndroidLogger as Koin Logger - default Level.INFO

            // use the Android context given there
            androidContext(this@BreakingBadApplication)

            modules(allModules)
        }
    }
}