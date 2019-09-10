package com.reyesmagos.squarefinder.core.di

import android.content.Context
import com.reyesmagos.squarefinder.core.CoroutineContextProvider
import com.reyesmagos.squarefinder.core.models.SecurityConfig
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

    /**
     * I will hardcode this here but this can be improved just adding a config field on the gradle file,
     * then the values can be obtained from a file not added to git
     * something like: BuildConfig.ClientID ...
     *
     */
    @Provides
    fun provideSecurityConfig(): SecurityConfig {
        return SecurityConfig(
            "YDCNSPGFAIPNO5TVFRAIGBOGT5ENFRBA4LWHJE3IR0XC3OWI",
            "RUNNHZXIRYK5BFMEQHIPB3L24H13TLXBHFMO24TXKAEXYQHQ"
        )
    }
}
