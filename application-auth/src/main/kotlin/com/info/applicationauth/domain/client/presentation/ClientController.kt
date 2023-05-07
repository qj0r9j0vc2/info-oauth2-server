package com.info.applicationauth.domain.client.presentation

import com.info.applicationauth.domain.client.business.ClientService
import com.info.applicationauth.domain.client.presentation.dto.request.RegisterClientRequest
import com.info.applicationauth.domain.client.presentation.dto.response.RegisterClientResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
class ClientController(
    private val clientService: ClientService
){

    @PostMapping("/client")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerClient(
        @RequestBody request: RegisterClientRequest,
        @RequestParam code: String
    ): RegisterClientResponse {
        return clientService.register(request, code)
    }

    @PutMapping("/client")
    fun sendEmailForClientRegistration(
        @RequestParam email: String
    ) {
        clientService.sendEmail(email)
    }


}
