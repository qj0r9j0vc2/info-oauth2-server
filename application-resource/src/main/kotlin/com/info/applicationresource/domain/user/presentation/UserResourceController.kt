package com.info.applicationresource.domain.user.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserResourceController {

    @GetMapping("/user/{email}")
    fun getUser(
        @PathVariable email: String
    ): String {
        return email
    }


}
