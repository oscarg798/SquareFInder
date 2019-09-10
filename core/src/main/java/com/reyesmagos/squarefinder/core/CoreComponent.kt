package com.reyesmagos.squarefinder.core

import dagger.Component

@Component(modules = [AppModule::class])
interface CoreComponent{

    fun provideCoroutineContextProvider(): CoroutineContextProvider
}
