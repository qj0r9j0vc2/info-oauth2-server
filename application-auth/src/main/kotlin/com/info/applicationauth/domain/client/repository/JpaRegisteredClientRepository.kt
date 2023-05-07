package com.info.applicationauth.domain.client.repository

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.info.applicationauth.domain.client.entity.Client
import org.springframework.security.jackson2.SecurityJackson2Modules
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import java.time.Duration
import java.util.function.Consumer


@Service
@Transactional
class JpaRegisteredClientRepository(
    private val clientRepository: ClientRepository,
): RegisteredClientRepository {
    private val objectMapper = ObjectMapper()
        .registerModules(SecurityJackson2Modules.getModules(JpaRegisteredClientRepository::class.java.classLoader))
        .registerModule(OAuth2AuthorizationServerJackson2Module())
        .registerModule(JavaTimeModule())


    override fun save(registeredClient: RegisteredClient) {
        clientRepository.save(toEntity(registeredClient))
    }

    override fun findById(id: String): RegisteredClient? {
        return clientRepository.findById(id).map { toObject(it) }.orElse(null)
    }

    override fun findByClientId(clientId: String): RegisteredClient? {
        return clientRepository.findByClientId(clientId).map {
                toObject(it)
            }.orElse(null)
    }


    private fun toObject(client: Client): RegisteredClient? {
        val clientAuthenticationMethods = StringUtils.commaDelimitedListToSet(
            client.clientAuthenticationMethods
        )
        val authorizationGrantTypes = StringUtils.commaDelimitedListToSet(
            client.authorizationGrantTypes
        )
        val redirectUris = StringUtils.commaDelimitedListToSet(
            client.redirectUris
        )
        val clientScopes = StringUtils.commaDelimitedListToSet(
            client.scopes
        )
        val builder = RegisteredClient.withId(client.id)
            .clientId(client.clientId)
            .clientIdIssuedAt(client.clientIdIssuedAt)
            .clientSecret(client.clientSecret)
            .clientSecretExpiresAt(client.clientSecretExpiresAt)
            .clientName(client.clientName)
            .clientAuthenticationMethods { authenticationMethods: MutableSet<ClientAuthenticationMethod?> ->
                clientAuthenticationMethods.forEach(
                    Consumer { authenticationMethod: String? ->
                        authenticationMethods.add(
                            resolveClientAuthenticationMethod(
                                authenticationMethod!!
                            )
                        )
                    })
            }
            .authorizationGrantTypes { grantTypes: MutableSet<AuthorizationGrantType?> ->
                authorizationGrantTypes.forEach(
                    Consumer { grantType: String? ->
                        grantTypes.add(
                            resolveAuthorizationGrantType(
                                grantType!!
                            )
                        )
                    })
            }
            .redirectUris { uris: MutableSet<String?> ->
                uris.addAll(
                    redirectUris
                )
            }
            .scopes { scopes: MutableSet<String?> ->
                scopes.addAll(
                    clientScopes
                )
            }
        val clientSettingsMap = parseMap(client.clientSettings)
        builder.clientSettings(ClientSettings.withSettings(clientSettingsMap).build())
        val tokenSettingsMap = parseMap(client.tokenSettings)
        builder.tokenSettings(TokenSettings.withSettings(tokenSettingsMap).build())
        return builder.build()
    }

    private fun toEntity(registeredClient: RegisteredClient): Client {
        val clientAuthenticationMethods: MutableList<String> =
            ArrayList(registeredClient.clientAuthenticationMethods.size)
        registeredClient.clientAuthenticationMethods.forEach(Consumer { clientAuthenticationMethod: ClientAuthenticationMethod ->
            clientAuthenticationMethods.add(
                clientAuthenticationMethod.value
            )
        })
        val authorizationGrantTypes: MutableList<String> = ArrayList(registeredClient.authorizationGrantTypes.size)
        registeredClient.authorizationGrantTypes.forEach(Consumer { authorizationGrantType: AuthorizationGrantType ->
            authorizationGrantTypes.add(
                authorizationGrantType.value
            )
        })

        return Client(
            registeredClient.id,
            registeredClient.clientId,
            registeredClient.clientIdIssuedAt,
            registeredClient.clientSecret,
            registeredClient.clientSecretExpiresAt,
            registeredClient.clientName,
            StringUtils.collectionToCommaDelimitedString(clientAuthenticationMethods),
            StringUtils.collectionToCommaDelimitedString(authorizationGrantTypes),
            StringUtils.collectionToCommaDelimitedString(registeredClient.redirectUris),
            StringUtils.collectionToCommaDelimitedString(registeredClient.scopes),
            writeMap(registeredClient.clientSettings.settings),
            writeMap(registeredClient.tokenSettings.settings)
        )
    }

    fun parseMap(data: String): Map<String, Any> {
        try {
            val map: MutableMap<String, Any> = HashMap<String, Any>()
            objectMapper.readValue(data, object : TypeReference<Map<String?, Any?>?>() {})?.forEach { (t, u) ->
                var v = u
                //Must check that type is "java.lang.Double".
                //java.lang.Double cannot cast to Duration.
                // so, you have to cast java.lang.Double as Double(kotlin).
                if (u is java.lang.Double) {
                    v = Duration.ofMillis((1000 * u.toDouble()).toLong())
                }
                if (t != null) {
                    if (v != null) {
                        map.put(t, v)
                    }
                }
            }
            return map
        } catch (ex: java.lang.Exception) {
            throw IllegalArgumentException(ex.message, ex)
        }
    }


    fun writeMap(data: Map<String, Any>): String {
        return try {
            objectMapper.writeValueAsString(data);
        } catch (ex: Exception) {
            throw IllegalArgumentException(ex.message, ex);
        }
    }


    private fun resolveAuthorizationGrantType(authorizationGrantType: String): AuthorizationGrantType {
        if (AuthorizationGrantType.AUTHORIZATION_CODE.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.AUTHORIZATION_CODE;
        } else if (AuthorizationGrantType.CLIENT_CREDENTIALS.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.CLIENT_CREDENTIALS;
        } else if (AuthorizationGrantType.REFRESH_TOKEN.getValue().equals(authorizationGrantType)) {
            return AuthorizationGrantType.REFRESH_TOKEN;
        }
        return AuthorizationGrantType(authorizationGrantType)
    }



    private fun resolveClientAuthenticationMethod(clientAuthenticationMethod: String): ClientAuthenticationMethod {
        if (ClientAuthenticationMethod.CLIENT_SECRET_BASIC.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
        } else if (ClientAuthenticationMethod.CLIENT_SECRET_POST.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.CLIENT_SECRET_POST;
        } else if (ClientAuthenticationMethod.NONE.getValue().equals(clientAuthenticationMethod)) {
            return ClientAuthenticationMethod.NONE;
        }
        return ClientAuthenticationMethod(clientAuthenticationMethod)
    }


}
