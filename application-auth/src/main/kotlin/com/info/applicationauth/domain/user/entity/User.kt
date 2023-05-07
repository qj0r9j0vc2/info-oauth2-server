package com.info.applicationauth.domain.user.entity

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "user")
class User(
    email: String,
    name: String,
    password: String
): UserDetails {

    @Id
    @Column(name = "email")
    val email: String = email

    @Column(name = "name")
    val name: String = name

    @Column(name = "password")
    private val password: String = password

    @ElementCollection(fetch = FetchType.EAGER)
    val roleList: MutableList<Role> = ArrayList()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return this.roleList.map {
            SimpleGrantedAuthority(it.mean)
        }.toMutableList()
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun getUsername(): String {
        return this.email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
