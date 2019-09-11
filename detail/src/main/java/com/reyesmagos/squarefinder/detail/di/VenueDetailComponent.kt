package com.reyesmagos.squarefinder.detail.di

import com.reyesmagos.squarefinder.core.di.CoreComponent
import com.reyesmagos.squarefinder.detail.VenueDetailActivity
import dagger.Component

@Component(dependencies = [CoreComponent::class], modules = [ViewDetailModule::class])
interface VenueDetailComponent {

    fun inject(venueDetailActivity: VenueDetailActivity)
}
