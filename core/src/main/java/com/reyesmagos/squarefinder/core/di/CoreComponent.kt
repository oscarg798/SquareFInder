package com.reyesmagos.squarefinder.core.di

import com.reyesmagos.squarefinder.core.CoroutineContextProvider
import com.reyesmagos.squarefinder.core.models.SecurityConfig
import dagger.Component
import retrofit2.Retrofit

@Component(modules = [AppModule::class, NetworkModule::class])
interface CoreComponent{

    fun provideCoroutineContextProvider(): CoroutineContextProvider

    fun provideRetrofit(): Retrofit

    fun provideSecurityConfig(): SecurityConfig
}
