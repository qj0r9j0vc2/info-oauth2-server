package com.info.applicationauth.domain.authorization.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.Table

const val serialVersionUID: Long = 1L

@Entity
@Table(name = "authorization_consent")
@IdClass(AuthorizationConsent.AuthorizationConsentId::class)
class AuthorizationConsent(
    registeredClientId: String,
    principalName: String,
    authorities: String
) {

    @Id
    val registeredClientId: String = registeredClientId

    @Id
    val principalName: String = principalName

    @Column(length = 100)
    val authorities: String = authorities


    class AuthorizationConsentId(
        val registeredClientId: String? = null,
        val principalName: String? = null
    ): java.io.Serializable {
        override fun equals(other: Any?): Boolean {
            if (this == other) return true
            if (other == null || javaClass != other.javaClass) return false
            val that = other as AuthorizationConsentId
            return registeredClientId == that.registeredClientId && principalName == that.principalName
        }
    }

}
