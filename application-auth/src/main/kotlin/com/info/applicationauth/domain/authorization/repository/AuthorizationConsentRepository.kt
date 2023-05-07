package com.info.applicationauth.domain.authorization.repository

import com.info.applicationauth.domain.authorization.entity.AuthorizationConsent
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AuthorizationConsentRepository: JpaRepository<AuthorizationConsent, AuthorizationConsent.AuthorizationConsentId> {

    fun findByRegisteredClientIdAndPrincipalName(registeredClientId: String, principalName: String): Optional<AuthorizationConsent>
    fun deleteByRegisteredClientIdAndPrincipalName(registeredClientId: String, principalName: String)
}
