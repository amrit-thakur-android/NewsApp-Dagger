package com.amritthakur.newsapp.di

import android.app.Application
import android.content.Context
import com.amritthakur.newsapp.common.DefaultDispatcherProvider
import com.amritthakur.newsapp.common.DispatcherProvider
import com.amritthakur.newsapp.navigation.NavigationChannel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    fun provideNavigationChannel(): NavigationChannel = NavigationChannel
}
