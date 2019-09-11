package com.reyesmagos.squarefinder.detail

import com.reyesmagos.squarefinder.core.contracts.BasePresenter
import com.reyesmagos.squarefinder.core.contracts.BaseView
import com.reyesmagos.squarefinder.core.models.ViewVenue

interface VenueDetailContract  {

    interface View: BaseView{

        fun showCoffeeName(name:String)

        fun showAddress(address: String)
    }

    interface Presenter: BasePresenter<View>{

        fun onViewReady(viewVenue: ViewVenue)
    }
}
