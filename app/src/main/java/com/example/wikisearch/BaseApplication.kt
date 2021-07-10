package com.example.wikisearch

import android.app.Application
import android.content.Context
import com.example.wikisearch.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {

    init {
        instance = this
    }
    companion object{

        private var instance: BaseApplication? = null
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }

    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(appModule)
        }
    }
}