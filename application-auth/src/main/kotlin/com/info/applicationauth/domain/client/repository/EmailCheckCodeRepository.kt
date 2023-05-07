package com.info.applicationauth.domain.client.repository

import com.info.applicationauth.domain.client.entity.EmailCheckCode
import org.springframework.data.repository.CrudRepository

interface EmailCheckCodeRepository: CrudRepository<EmailCheckCode, String> {
}
