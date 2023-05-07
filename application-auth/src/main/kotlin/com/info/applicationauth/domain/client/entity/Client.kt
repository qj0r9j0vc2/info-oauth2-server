package com.info.applicationauth.domain.client.entity

import jakarta.persistence.*
import java.time.Instant


@Entity
@Table(name = "client")
class Client(
    id: String,
    clientId: String,
    clientIdIssuedAt: Instant?,
    clientSecret: String?,
    clientSecretExpiresAt: Instant?,
    clientName: String,
    clientAuthenticationMethods: String,
    authorizationGrantTypes: String,
    redirectUris: String,
    scopes: String,
    clientSettings: String,
    tokenSettings: String

) {

    @Id
    val id: String = id

    val clientId: String = clientId

    val clientIdIssuedAt: Instant? = clientIdIssuedAt

    val clientSecret: String? = clientSecret

    val clientSecretExpiresAt: Instant? = clientSecretExpiresAt

    val clientName: String = clientName

    @Column(length = 1000)
    val clientAuthenticationMethods: String = clientAuthenticationMethods

    @Column(length = 1000)
    val authorizationGrantTypes: String = authorizationGrantTypes

    @Column(length = 1000)
    val redirectUris: String = redirectUris

    @Column(length = 1000)
    val scopes: String = scopes

    @Column(length = 2000)
    val clientSettings: String = clientSettings

    @Column(length = 2000)
    val tokenSettings: String = tokenSettings

}
