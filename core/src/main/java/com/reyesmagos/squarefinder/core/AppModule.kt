package com.reyesmagos.squarefinder.core

import android.content.Context
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
        return CoroutineContextProvider(Dispatchers.Main, Dispatchers.IO)
    }
}
