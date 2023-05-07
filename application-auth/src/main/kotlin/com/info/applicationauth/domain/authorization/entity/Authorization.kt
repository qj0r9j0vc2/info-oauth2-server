package com.info.applicationauth.domain.authorization.entity

import jakarta.persistence.*
import java.time.Instant
import javax.xml.crypto.Data


@Entity
@Table(name = "authorization")
class Authorization(
    id: String,
    registeredClientId: String,
    principalName: String,
    authorizationGrantType: String,
    authorizedScopes: String,
    attributes: String,
    state: String?
) {

    @Id
    @Column
    val id: String = id
    val registeredClientId: String = registeredClientId
    val principalName: String = principalName
    val authorizationGrantType: String = authorizationGrantType

    @Column(length = 1000)
    val authorizedScopes: String = authorizedScopes

    @Lob
    @Column(length = 4000)
    val attributes: String = attributes

    @Column(length = 500)
    val state: String? = state

    @Lob
    @Column(length = 4000)
    var authorizationCodeValue: String? = null
        protected set

    var authorizationCodeIssuedAt: Instant? = null
        protected set

    var authorizationCodeExpiresAt: Instant? = null
        protected set

    var authorizationCodeMetadata: String? = null
        protected set

    @Lob
    @Column(length = 4000)
    var accessTokenValue: String? = null
        protected set

    var accessTokenIssuedAt: Instant? = null
        protected set

    var accessTokenExpiresAt: Instant? = null
        protected set

    @Lob
    @Column(length = 2000)
    var accessTokenMetadata: String? = null
        protected set
    val accessTokenType: String? = null

    @Column(length = 1000)
    var accessTokenScopes: String? = null
        protected set

    @Lob
    @Column(length = 4000)
    var refreshTokenValue: String? = null
        protected set

    var refreshTokenIssuedAt: Instant? = null
        protected set

    var refreshTokenExpiresAt: Instant? = null
        protected set

    @Lob
    @Column(length = 2000)
    var refreshTokenMetadata: String? = null
        protected set

    @Lob
    @Column(length = 4000)
    var oidcIdTokenValue: String? = null
        protected set

    var oidcIdTokenIssuedAt: Instant? = null
        protected set

    var oidcIdTokenExpiresAt: Instant? = null
        protected set

    @Lob
    @Column(length = 2000)
    var oidcIdTokenMetadata: String? = null
        protected set

    @Lob
    @Column(length = 2000)
    var oidcIdTokenClaims: String? = null
        protected set

    fun resetOidcIdTokenClaims(claims: String) {
        this.oidcIdTokenClaims = claims
    }

    fun resetOidcIdTokenValue(oidcIdTokenValue: String) {
        this.oidcIdTokenValue = oidcIdTokenValue
    }

    fun resetOidcIdTokenIssuedAt(oidcIdTokenIssuedAt: Instant) {
        this.oidcIdTokenIssuedAt = oidcIdTokenIssuedAt
    }

    fun resetOidcIdTokenExpiresAt(oidcIdTokenExpiresAt: Instant) {
        this.oidcIdTokenExpiresAt = oidcIdTokenExpiresAt
    }

    fun resetOidcIdTokenMetadata(oidcIdTokenMetadata: String) {
        this.oidcIdTokenMetadata = oidcIdTokenMetadata
    }

    fun resetRefreshTokenValue(refreshTokenValue: String) {
        this.refreshTokenValue = refreshTokenValue
    }

    fun resetRefreshTokenIssuedAt(refreshTokenIssuedAt: Instant) {
        this.refreshTokenIssuedAt = refreshTokenIssuedAt
    }

    fun resetRefreshTokenExpiresAt(refreshTokenExpiresAt: Instant) {
        this.refreshTokenExpiresAt = refreshTokenExpiresAt
    }

    fun resetRefreshTokenMetadata(refreshTokenMetadata: String) {
        this.refreshTokenMetadata = refreshTokenMetadata
    }

    fun resetAccessTokenScopes(accessTokenScopes: String) {
        this.accessTokenScopes = accessTokenScopes
    }

    fun resetAccessTokenMetadata(accessTokenMetadata: String) {
        this.accessTokenMetadata = accessTokenMetadata
    }

    fun resetAccessTokenExpiresAt(accessTokenExpiresAt: Instant) {
        this.accessTokenExpiresAt = accessTokenExpiresAt
    }

    fun resetAccessTokenIssuedAt(accessTokenIssuedAt: Instant) {
        this.accessTokenIssuedAt = accessTokenIssuedAt
    }

    fun resetAccessTokenValue(accessTokenValue: String) {
        this.accessTokenValue = accessTokenValue
    }

    fun resetAuthorizationCodeValue(authorizationCodeValue: String) {
        this.authorizationCodeValue = authorizationCodeValue
    }

    fun resetAuthorizationCodeIssuedAt(authorizationCodeIssuedAt: Instant) {
        this.authorizationCodeIssuedAt = authorizationCodeIssuedAt
    }

    fun resetAuthorizationCodeExpiresAt(authorizationCodeExpiresAt: Instant) {
        this.authorizationCodeExpiresAt = authorizationCodeExpiresAt
    }

    fun resetAuthorizationCodeMetadata(authorizationCodeMetadata: String) {
        this.authorizationCodeMetadata = authorizationCodeMetadata
    }


}
