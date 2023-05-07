package com.info.applicationauth.domain.client.business

import com.info.applicationauth.domain.client.presentation.dto.request.RegisterClientRequest
import com.info.applicationauth.domain.client.presentation.dto.response.RegisterClientResponse

interface ClientService {

    fun register(request: RegisterClientRequest, code: String): RegisterClientResponse
    fun sendEmail(email: String)

}

