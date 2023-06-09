package com.info.applicationauth.domain.client.presentation.dto.request

data class RegisterClientRequest(
    val clientEmail: String,
    val redirectUris: List<String>,
    val serviceName: String,
    val serviceDomainName: String,
    val requireProofKey: Boolean
)
