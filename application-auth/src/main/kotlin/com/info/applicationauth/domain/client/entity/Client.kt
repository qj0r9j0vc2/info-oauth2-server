package com.info.applicationauth.domain.client.entity

import jakarta.persistence.*
import java.time.Instant


@Entity
@Table(name = "client")
class Client(
    id: String,
    clientId: String,
    clientIdIssuedAt: Instant?,
    clientSecret: String,
    clientSecretExpiresAt: Instant?,
    clientEmail: String,
    clientServiceDomainName: String,
    clientAuthenticationMethods: String,
    authorizationGrantTypes: String,
    redirectUris: String,
    scopes: String,
    clientSettings: String,
    tokenSettings: String

) {

    @Id
    val id: String = clientId

    @Column(name = "client_id", nullable = false)
    val clientId: String = clientId

    @Column(name = "client_id_issued_at")
    val clientIdIssuedAt: Instant? = clientIdIssuedAt

    @Column(name = "client_secret", nullable = false)
    val clientSecret: String = clientSecret

    @Column(name = "client_secret_expires_at")
    val clientSecretExpiresAt: Instant? = clientSecretExpiresAt

    @Column(name = "client_name", nullable = false)
    val clientEmail: String = clientEmail

    @Column(name = "client_service_domain_name", nullable = false)
    val clientServiceDomainName: String = clientServiceDomainName

    @Column(name = "client_authentication_method_list", length = 1000, nullable = false)
    val clientAuthenticationMethods: String = clientAuthenticationMethods

    @Column(name = "authorization_grant_type_list", length = 1000, nullable = false)
    val authorizationGrantTypes: String = authorizationGrantTypes

    @Column(name = "redirect_uri_list", length = 1000, nullable = false)
    val redirectUris: String = redirectUris

    @Column(name = "scope_list", length = 1000, nullable = false)
    val scopes: String = scopes

    @Column(name = "client_setting_list", length = 2000, nullable = false)
    val clientSettings: String = clientSettings

    @Column(name = "token_setting_list", length = 2000, nullable = false)
    val tokenSettings: String = tokenSettings

}
