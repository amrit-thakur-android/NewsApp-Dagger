package com.amritthakur.newsapp

import android.app.Application
import com.amritthakur.newsapp.di.ApplicationComponent
import com.amritthakur.newsapp.di.DaggerApplicationComponent

class NewsApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
            .application(this)
            .build()
    }
}
