package com.stevdza_san.currencyapp

import android.app.Application
import di.appModule
import di.initializeKoin
import di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CurrencyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin {
            androidContext(androidContext = this@CurrencyApplication)
        }
    }
}
