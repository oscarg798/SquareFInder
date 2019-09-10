package com.reyesmagos.squarefinder.core

import com.reyesmagos.squarefinder.core.di.CoreComponent

interface CoreComponentProvider {

    fun getCoreComponent(): CoreComponent
}
