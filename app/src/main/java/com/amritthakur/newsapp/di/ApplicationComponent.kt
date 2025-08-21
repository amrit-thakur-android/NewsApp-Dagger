package com.amritthakur.newsapp.di

import android.app.Application
import com.amritthakur.newsapp.NewsApplication
import com.amritthakur.newsapp.navigation.NavigationChannel
import com.amritthakur.newsapp.viewmodel.CountriesViewModel
import com.amritthakur.newsapp.viewmodel.HomeViewModel
import com.amritthakur.newsapp.viewmodel.LanguagesViewModel
import com.amritthakur.newsapp.viewmodel.NewsSourcesViewModel
import com.amritthakur.newsapp.viewmodel.NewsViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        DispatcherModule::class,
    ]
)
interface ApplicationComponent {

    fun inject(application: NewsApplication)

    // Expose functions
    fun homeViewModel(): HomeViewModel
    fun newsViewModel(): NewsViewModel
    fun newsSourcesViewModel(): NewsSourcesViewModel
    fun countriesViewModel(): CountriesViewModel
    fun languagesViewModel(): LanguagesViewModel
    fun navigationChannel(): NavigationChannel

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}
