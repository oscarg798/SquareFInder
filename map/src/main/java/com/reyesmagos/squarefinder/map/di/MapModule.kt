package com.reyesmagos.squarefinder.map.di

import com.reyesmagos.squarefinder.core.CoroutineContextProvider
import com.reyesmagos.squarefinder.core.Interactor
import com.reyesmagos.squarefinder.core.models.SecurityConfig
import com.reyesmagos.squarefinder.core.models.Venue
import com.reyesmagos.squarefinder.map.MapActivityPresenter
import com.reyesmagos.squarefinder.map.MapContract
import com.reyesmagos.squarefinder.map.interactors.GetVenueInteractor
import com.reyesmagos.squarefinder.map.models.GetVenueParams
import com.reyesmagos.squarefinder.map.providers.LocationProvider
import com.reyesmagos.squarefinder.map.providers.LocationProviderImpl
import com.reyesmagos.squarefinder.map.providers.LocationSettingsProvider
import com.reyesmagos.squarefinder.map.providers.LocationSettingsProviderImpl
import com.reyesmagos.squarefinder.map.repository.VenueRepository
import com.reyesmagos.squarefinder.map.repository.VenueRepositoryImpl
import com.reyesmagos.squarefinder.map.service.GetVenueService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

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
    fun provideGetVenueService(retrofit: Retrofit): GetVenueService {
        return retrofit.create(GetVenueService::class.java)
    }

    @Provides
    fun provideVenueRepository(
        getVenueService: GetVenueService,
        securityConfig: SecurityConfig
    ): VenueRepository {
        return VenueRepositoryImpl(getVenueService, securityConfig)
    }

    @Provides
    fun provideGetVenueInteractor(getVenueRepository: VenueRepository): Interactor<List<Venue>, GetVenueParams> {
        return GetVenueInteractor(getVenueRepository)
    }

    @Provides
    fun provideMapPresenter(
        coroutineContextProvider: CoroutineContextProvider,
        getVenueInteractor: Interactor<List<Venue>, GetVenueParams>
    ): MapContract.Presenter {
        return MapActivityPresenter(getVenueInteractor, coroutineContextProvider)
    }

}
