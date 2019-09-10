package com.reyesmagos.squarefinder.core.di

import android.content.Context
import com.reyesmagos.squarefinder.core.CoroutineContextProvider
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class AppModule(private val contextApplication: Context) {

    @Provides
    fun providerContext(): Context {
        return contextApplication
    }

    @Provides
    fun provideCoroutineContextProvider(): CoroutineContextProvider {
        return CoroutineContextProvider(
            Dispatchers.Main,
            Dispatchers.IO
        )
    }

    @Provides
    fun provideBaseUrl(): String {
        return "https://api.foursquare.com/v2/"
    }
}
