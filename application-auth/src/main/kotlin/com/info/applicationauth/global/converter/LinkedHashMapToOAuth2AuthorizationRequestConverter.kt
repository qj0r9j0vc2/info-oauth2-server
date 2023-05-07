package com.info.applicationauth.global.converter

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.convert.converter.Converter
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.stereotype.Service
import java.util.LinkedHashMap

@Service
class LinkedHashMapToOAuth2AuthorizationRequestConverter(
): Converter<java.util.LinkedHashMap<*, *>, OAuth2AuthorizationRequest> {
    private val objectMapper: ObjectMapper = ObjectMapper()

    override fun convert(source: LinkedHashMap<*, *>): OAuth2AuthorizationRequest? {
        return objectMapper.convertValue(source, OAuth2AuthorizationRequest::class.java)
    }

}
