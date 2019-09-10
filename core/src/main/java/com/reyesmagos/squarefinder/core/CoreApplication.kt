package com.reyesmagos.squarefinder.core

import android.app.Application
import com.reyesmagos.squarefinder.core.di.AppModule
import com.reyesmagos.squarefinder.core.di.CoreComponent
import com.reyesmagos.squarefinder.core.di.DaggerCoreComponent
import com.reyesmagos.squarefinder.core.exceptions.CoreComponentNotFoundException

class CoreApplication : Application(), CoreComponentProvider {

    private var coreComponent: CoreComponent? = null

    override fun getCoreComponent(): CoreComponent {
        if (coreComponent == null) {
            coreComponent = DaggerCoreComponent.builder()
                .appModule(AppModule(this))
                .build()
        }

        return coreComponent ?: throw CoreComponentNotFoundException()
    }
}
