package com.info.applicationauth.domain.client.presentation.dto.response

data class RegisterClientResponse (
    val clientName: String,
    val clientId: String,
    val clientSecret: String,
    val redirectUris: Set<String>

)
