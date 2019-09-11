package com.reyesmagos.squarefinder.detail

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import com.reyesmagos.squarefinder.core.CoreApplication


class CustomTestRunner : AndroidJUnitRunner() {

    @Throws(Exception::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {
        return super.newApplication(
            cl,
            CoreApplication::class.java.name,
            context
        )
    }
}
