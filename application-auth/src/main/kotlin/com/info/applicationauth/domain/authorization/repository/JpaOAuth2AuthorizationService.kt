package com.info.applicationauth.domain.authorization.repository

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.info.applicationauth.domain.authorization.entity.Authorization
import com.info.applicationauth.domain.user.entity.User
import com.info.applicationauth.domain.user.entity.UserMixin
import org.springframework.dao.DataRetrievalFailureException
import org.springframework.security.jackson2.SecurityJackson2Modules
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.OAuth2RefreshToken
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import java.time.Instant
import java.util.*
import java.util.function.Consumer


@Service
@Transactional
class JpaOAuth2AuthorizationService(
    private val authorizationRepository: AuthorizationRepository,
    private val registeredClientRepository: RegisteredClientRepository
): OAuth2AuthorizationService {

    private val objectMapper = ObjectMapper()
        .registerModules(SecurityJackson2Modules.getModules(JpaOAuth2AuthorizationService::class.java.classLoader))
        .registerModule(OAuth2AuthorizationServerJackson2Module())
        .addMixIn(User::class.java, UserMixin::class.java)

    override fun save(authorization: OAuth2Authorization) {
        authorizationRepository.save(toEntity(authorization))
    }

    override fun remove(authorization: OAuth2Authorization) {
        authorizationRepository.deleteById(authorization.id)
    }

    override fun findById(id: String): OAuth2Authorization? {
        return authorizationRepository.findById(id).map { toObject(it) }.orElse(null)
    }

    override fun findByToken(token: String?, tokenType: OAuth2TokenType?): OAuth2Authorization? {

        val result: Optional<Authorization> = if (tokenType == null) {
            authorizationRepository.findByStateOrAuthorizationCodeValueOrAccessTokenValueOrRefreshTokenValue(token)
        } else if (OAuth2ParameterNames.STATE == tokenType.value) {
            authorizationRepository.findByState(token!!)
        } else if (OAuth2ParameterNames.CODE == tokenType.value) {
            authorizationRepository.findByAuthorizationCodeValue(token!!)
        } else if (OAuth2ParameterNames.ACCESS_TOKEN == tokenType.value) {
            authorizationRepository.findByAccessTokenValue(token!!)
        } else if (OAuth2ParameterNames.REFRESH_TOKEN == tokenType.value) {
            authorizationRepository.findByRefreshTokenValue(token!!)
        } else {
            Optional.empty()
        }

        return result.map(::toObject).orElse(null)
    }

    private fun toObject(entity: Authorization): OAuth2Authorization? {
        val registeredClient = registeredClientRepository.findById(entity.registeredClientId)
            ?: throw DataRetrievalFailureException(
                "The RegisteredClient with id '" + entity.registeredClientId + "' was not found in the RegisteredClientRepository."
            )
        val builder = OAuth2Authorization.withRegisteredClient(registeredClient)
            .id(entity.id)
            .principalName(entity.principalName)
            .authorizationGrantType(resolveAuthorizationGrantType(entity.authorizationGrantType))
            .authorizedScopes(StringUtils.commaDelimitedListToSet(entity.authorizedScopes))
            .attributes { attributes: MutableMap<String?, Any?> ->
                parseMap(entity.attributes)?.let {
                    attributes.putAll(
                        it
                    )
                }
            }
        if (entity.state != null) {
            builder.attribute(OAuth2ParameterNames.STATE, entity.state)
        }
        if (entity.authorizationCodeValue != null) {
            val authorizationCode = OAuth2AuthorizationCode(
                entity.authorizationCodeValue,
                entity.authorizationCodeIssuedAt,
                entity.authorizationCodeExpiresAt
            )
            builder.token(
                authorizationCode
            ) { metadata: MutableMap<String?, Any?> ->
                entity.authorizationCodeMetadata?.let {
                    parseMap(it)?.let {
                        metadata.putAll(
                            it
                        )
                    }
                }
            }
        }
        if (entity.accessTokenValue != null) {
            val accessToken = OAuth2AccessToken(
                OAuth2AccessToken.TokenType.BEARER,
                entity.accessTokenValue,
                entity.accessTokenIssuedAt,
                entity.accessTokenExpiresAt,
                StringUtils.commaDelimitedListToSet(entity.accessTokenScopes)
            )
            builder.token(
                accessToken
            ) { metadata: MutableMap<String?, Any?> ->
                entity.accessTokenMetadata?.let {
                    parseMap(it)?.let {
                        metadata.putAll(
                            it
                        )
                    }
                }
            }
        }
        if (entity.refreshTokenValue != null) {
            val refreshToken = OAuth2RefreshToken(
                entity.refreshTokenValue,
                entity.refreshTokenIssuedAt,
                entity.refreshTokenExpiresAt
            )
            builder.token(
                refreshToken
            ) { metadata: MutableMap<String?, Any?> ->
                entity.refreshTokenMetadata?.let {
                    parseMap(it)?.let {
                        metadata.putAll(
                            it
                        )
                    }
                }
            }
        }
        if (entity.oidcIdTokenValue != null) {
            val idToken = OidcIdToken(
                entity.oidcIdTokenValue,
                entity.oidcIdTokenIssuedAt,
                entity.oidcIdTokenExpiresAt,
                entity.oidcIdTokenClaims?.let { parseMap(it) }
            )
            builder.token(
                idToken
            ) { metadata: MutableMap<String?, Any?> ->
                entity.oidcIdTokenMetadata?.let {
                    parseMap(it)?.let {
                        metadata.putAll(
                            it
                        )
                    }
                }
            }
        }
        return builder.build()
    }

    private fun toEntity(a: OAuth2Authorization): Authorization {
        val entity = Authorization(
            a.id,
            a.registeredClientId,
            a.principalName,
            a.authorizationGrantType.value,
            StringUtils.collectionToDelimitedString(a.authorizedScopes, ","),
            writeMap(a.attributes),
            a.getAttribute(OAuth2ParameterNames.STATE)
        )
        val authorizationCode = a.getToken(
            OAuth2AuthorizationCode::class.java
        )
        setTokenValues(
            authorizationCode,
            entity::resetAuthorizationCodeValue,
            entity::resetAuthorizationCodeIssuedAt,
            entity::resetAuthorizationCodeExpiresAt,
            entity::resetAuthorizationCodeMetadata
        )
        val accessToken = a.getToken(
            OAuth2AccessToken::class.java
        )
        setTokenValues(
            accessToken,
            entity::resetAccessTokenValue,
            entity::resetAccessTokenIssuedAt,
            entity::resetAccessTokenExpiresAt,
            entity::resetAccessTokenMetadata
        )
        if (accessToken != null && accessToken.token.scopes != null) {
            entity.resetAccessTokenScopes(StringUtils.collectionToDelimitedString(accessToken.token.scopes, ","))
        }
        val refreshToken = a.getToken(
            OAuth2RefreshToken::class.java
        )
        setTokenValues(
            refreshToken,
            entity::resetRefreshTokenValue,
            entity::resetRefreshTokenIssuedAt,
            entity::resetRefreshTokenExpiresAt,
            entity::resetRefreshTokenMetadata
        )
        val oidcIdToken = a.getToken(OidcIdToken::class.java)
        setTokenValues(
            oidcIdToken,
            entity::resetOidcIdTokenValue,
            entity::resetOidcIdTokenIssuedAt,
            entity::resetOidcIdTokenExpiresAt,
            entity::resetOidcIdTokenMetadata
        )
        oidcIdToken?.let {
            it.claims?.let {
                entity.resetOidcIdTokenClaims(writeMap(it))
            }
        }
        return entity
    }

    private fun setTokenValues(
        token: OAuth2Authorization.Token<*>?,
        tokenValueConsumer: Consumer<String>,
        issuedAtConsumer: Consumer<Instant>,
        expiresAtConsumer: Consumer<Instant>,
        metadataConsumer: Consumer<String>
    ) {
        if (token != null) {
            val oAuth2Token = token.token
            tokenValueConsumer.accept(oAuth2Token.tokenValue)
            oAuth2Token.issuedAt?.let { issuedAtConsumer.accept(it) }
            oAuth2Token.expiresAt?.let { expiresAtConsumer.accept(it) }
            metadataConsumer.accept(writeMap(token.metadata))
        }
    }

    private fun parseMap(data: String): Map<String?, Any?>? {
        return try {
            objectMapper.readValue(data, object : TypeReference<Map<String?, Any?>?>() {})
        } catch (ex: Exception) {
            throw IllegalArgumentException(ex.message, ex)
        }
    }

    private fun writeMap(metadata: Map<String, Any>): String {
        return try {
            objectMapper.writeValueAsString(metadata)
        } catch (ex: Exception) {
            throw IllegalArgumentException(ex.message, ex)
        }
    }

    private fun resolveAuthorizationGrantType(authorizationGrantType: String): AuthorizationGrantType {
        if (AuthorizationGrantType.AUTHORIZATION_CODE.value == authorizationGrantType) {
            return AuthorizationGrantType.AUTHORIZATION_CODE
        } else if (AuthorizationGrantType.CLIENT_CREDENTIALS.value == authorizationGrantType) {
            return AuthorizationGrantType.CLIENT_CREDENTIALS
        } else if (AuthorizationGrantType.REFRESH_TOKEN.value == authorizationGrantType) {
            return AuthorizationGrantType.REFRESH_TOKEN
        }
        return AuthorizationGrantType(authorizationGrantType)
    }
}
