package com.info.applicationauth.domain.client.business

import com.info.applicationauth.domain.client.entity.EmailCheckCode
import com.info.applicationauth.domain.client.presentation.dto.request.RegisterClientRequest
import com.info.applicationauth.domain.client.presentation.dto.response.RegisterClientResponse
import com.info.applicationauth.domain.client.repository.EmailCheckCodeRepository
import com.info.applicationauth.domain.client.repository.JpaRegisteredClientRepository
import com.info.applicationauth.infra.smtp.EmailUtil
import com.info.applicationcore.exception.CommonException
import com.info.applicationcore.exception.ErrorCode
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
@Transactional
class ClientServiceImpl(
    private val passwordEncoder: PasswordEncoder,
    private val jpaRegisteredClientRepository: JpaRegisteredClientRepository,
    private val emailUtil: EmailUtil,
    private val emailCheckCodeRepository: EmailCheckCodeRepository
): ClientService {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun register(request: RegisterClientRequest, code: String): RegisterClientResponse {
        if (emailCheckCodeRepository.findById(request.clientEmail).orElse(null)?.data == code) {

            val clientId = UUID.randomUUID().toString()
            val clientSecret = UUID.randomUUID().toString()

            jpaRegisteredClientRepository.save(
                RegisteredClient.withId(UUID.randomUUID().toString())
                    .clientName(request.clientEmail)
                    .clientId(clientId)
                    .clientSecret(passwordEncoder.encode(clientSecret))
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                    .redirectUri(request.redirectUri)
                    .scope("test-scope")
                    .build()
            )
            log.info("clientName: ${request.clientEmail} clientId: $clientId, clientSecret: $clientSecret")

            return RegisterClientResponse(
                request.clientEmail,
                clientId,
                clientSecret,
                request.redirectUri
            )
        } else throw CommonException(errorCode = ErrorCode.NOT_MATCH_ERROR)
    }

    override fun sendEmail(email: String) {
        val code = Random().nextInt(1000, 9999).toString()

        emailUtil.sendEmail(email, "Email code for Client registration",
            "Email check code for info OAuth2 Client Registration is $code")
        emailCheckCodeRepository.save(
            EmailCheckCode(
                email,
                code
            )
        )
    }


}

