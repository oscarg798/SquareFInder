package com.reyesmagos.squarefinder.map.di

import com.reyesmagos.squarefinder.core.di.CoreComponent
import com.reyesmagos.squarefinder.map.MapActivity
import dagger.Component

@Component(modules = [MapModule::class], dependencies = [CoreComponent::class])
interface MapComponent {

    fun inject(mapActivity: MapActivity)
}
