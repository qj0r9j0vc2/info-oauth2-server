package com.info.applicationauth.domain.client.repository

import com.info.applicationauth.domain.client.entity.Client
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ClientRepository: JpaRepository<Client, String> {

    fun findByClientId(clientId: String): Optional<Client>
}
