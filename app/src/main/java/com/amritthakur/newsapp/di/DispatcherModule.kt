package com.amritthakur.newsapp.di

import com.amritthakur.newsapp.common.DefaultDispatcherProvider
import com.amritthakur.newsapp.common.DispatcherProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DispatcherModule {

    @Provides
    @Singleton
    fun bindDispatcherProvider(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }
}
