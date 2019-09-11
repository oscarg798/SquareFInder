package com.reyesmagos.squarefinder.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.airbnb.deeplinkdispatch.DeepLink
import com.reyesmagos.squarefinder.core.ARGUMENT_VIEW_VENUE
import com.reyesmagos.squarefinder.core.CoreComponentProvider
import com.reyesmagos.squarefinder.core.DETAIL_DEEP_LINK
import com.reyesmagos.squarefinder.core.models.ViewVenue
import com.reyesmagos.squarefinder.detail.di.DaggerVenueDetailComponent
import com.reyesmagos.squarefinder.detail.di.ViewDetailModule
import com.reyesmagos.squarefinder.detail.exceptions.ViewVenueNoPassedAsParameterException
import kotlinx.android.synthetic.main.activity_venue_detail.*
import javax.inject.Inject

@DeepLink(DETAIL_DEEP_LINK)
class VenueDetailActivity : AppCompatActivity(), VenueDetailContract.View {

    @Inject
    lateinit var presenter: VenueDetailContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue_detail)

        DaggerVenueDetailComponent.builder()
            .coreComponent((application as CoreComponentProvider).getCoreComponent())
            .viewDetailModule(ViewDetailModule())
            .build()
            .inject(this)

        val arguments = intent?.getParcelableExtra<ViewVenue>(ARGUMENT_VIEW_VENUE) ?: throw ViewVenueNoPassedAsParameterException()
        presenter.bind(this)
        presenter.onViewReady(arguments)
    }

    override fun showCoffeeName(name: String) {
        tvName?.text = name
    }

    override fun showAddress(address: String) {
        tvAddress?.text = address
    }
}
