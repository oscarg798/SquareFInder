package com.reyesmagos.squarefinder.core

interface Interactor<Response, Params> where Response : Any {

    suspend operator fun invoke(
        params: Params
    ): Response
}
