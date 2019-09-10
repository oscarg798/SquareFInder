package com.reyesmagos.squarefinder.map.di

import com.reyesmagos.squarefinder.core.CoroutineContextProvider
import com.reyesmagos.squarefinder.map.MapActivityPresenter
import com.reyesmagos.squarefinder.map.MapContract
import com.reyesmagos.squarefinder.map.providers.LocationProvider
import com.reyesmagos.squarefinder.map.providers.LocationProviderImpl
import com.reyesmagos.squarefinder.map.providers.LocationSettingsProvider
import com.reyesmagos.squarefinder.map.providers.LocationSettingsProviderImpl
import dagger.Module
import dagger.Provides

@Module
class MapModule {

    @Provides
    fun provideLocationProvider(): LocationProvider {
        return LocationProviderImpl()
    }

    @Provides
    fun provideLocationSettingsProvider(): LocationSettingsProvider {
        return LocationSettingsProviderImpl()
    }

    @Provides
    fun provideMapPresenter(coroutineContextProvider: CoroutineContextProvider): MapContract.Presenter {
        return MapActivityPresenter(coroutineContextProvider)
    }

}
