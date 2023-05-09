package com.info.applicationresource.domain.business

import com.info.applicationresource.domain.presentation.dto.response.UserResponse

interface ResourceService {

    fun loadUser(claims: Map<String, Any>): UserResponse
}
