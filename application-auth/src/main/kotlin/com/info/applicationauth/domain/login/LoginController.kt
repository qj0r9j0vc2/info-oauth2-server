package com.info.applicationauth.domain.login

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
class LoginController {


    @GetMapping("/info-login")
    fun login(): String {
        return "login"
    }

    @GetMapping("/info-login-fail")
    fun loginFailed(@RequestParam("exception") exception: String?): String? {
        return "redirect:/info-login?error=${exception?:"invalid request"}"
    }

}
