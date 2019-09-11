package com.reyesmagos.squarefinder.detail.di

import com.reyesmagos.squarefinder.core.CoroutineContextProvider
import com.reyesmagos.squarefinder.detail.VenueDetailActivityPresenter
import com.reyesmagos.squarefinder.detail.VenueDetailContract
import dagger.Module
import dagger.Provides

@Module
class ViewDetailModule {

    @Provides
    fun provideViewDetailActivityPresenter(coroutineContextProvider: CoroutineContextProvider): VenueDetailContract.Presenter {
        return VenueDetailActivityPresenter(
            coroutineContextProvider
        )
    }
}
