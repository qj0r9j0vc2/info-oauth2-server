package com.info.applicationauth.domain.client.presentation.dto.request

data class RegisterClientRequest(
    val clientEmail: String,
    val redirectUri: String

)

//{
//    "clientName": "jinwoo794533@gmail.com",
//    "clientId": "975e474d-6158-4076-bd88-9df542fa7c9c",
//    "clientSecret": "1b96a446-46b7-4b3d-be7d-2cb07515da96",
//    "redirectUri": "http://localhost:8000"
//}
