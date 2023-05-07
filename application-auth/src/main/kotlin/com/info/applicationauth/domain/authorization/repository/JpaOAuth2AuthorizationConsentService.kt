package com.info.applicationauth.domain.authorization.repository

import com.info.applicationauth.domain.authorization.entity.AuthorizationConsent
import org.springframework.dao.DataRetrievalFailureException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils

//@Service
//@Transactional
//class JpaOAuth2AuthorizationConsentService(
//    private val authorizationConsentRepository: AuthorizationConsentRepository,
//    private val registeredClientRepository: RegisteredClientRepository
//): OAuth2AuthorizationConsentService {
//
//    override fun save(authorizationConsent: OAuth2AuthorizationConsent) {
//        authorizationConsentRepository.save(toEntity(authorizationConsent))
//    }
//
//    override fun remove(authorizationConsent: OAuth2AuthorizationConsent) {
//        authorizationConsentRepository.deleteByRegisteredClientIdAndPrincipalName(
//            authorizationConsent.registeredClientId,
//            authorizationConsent.principalName
//        )
//    }
//
//    override fun findById(registeredClientId: String, principalName: String): OAuth2AuthorizationConsent? {
//        return authorizationConsentRepository.findByRegisteredClientIdAndPrincipalName(
//            registeredClientId,
//            principalName
//        ).map {
//            toObject(it)
//        }.orElse(null)
//    }
//
//    private fun toObject(a: AuthorizationConsent): OAuth2AuthorizationConsent {
//        val registeredClient = registeredClientRepository.findByClientId(a.registeredClientId)
//            ?: throw DataRetrievalFailureException("RegisteredClient not found: " + a.registeredClientId)
//
//        val builder = OAuth2AuthorizationConsent.withId(
//            registeredClient.id, a.principalName
//        )
//        for (authority in StringUtils.commaDelimitedListToSet(a.authorities)) {
//            builder.authority(SimpleGrantedAuthority(authority))
//        }
//        return builder.build()
//    }
//
//    private fun toEntity(a: OAuth2AuthorizationConsent): AuthorizationConsent {
//        val entity = AuthorizationConsent(
//            a.registeredClientId,
//            a.principalName,
//            StringUtils.collectionToCommaDelimitedString(
//                a.authorities.map {
//                    it.authority
//                }
//            )
//        )
//        return entity
//    }
//
//}
