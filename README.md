# **info-oauth2-server**

https://oauth2.info-dsm.info/
[자세한 설명(한글)](./README_ko.md)

---

> info-oauth2 provides user data from INFO service that is DSM job site as OAuth2 service

| authorizationCodeTimeToLive | 5minutes |  |
| --- | --- | --- |
| accessTokenTimeToLive | 1hours |  |
| accessTokenFormat | self-contained | Self-contained tokens use a protected, time-limited data structure that contains token metadata and claims of the user and/or client. JSON Web Token (JWT) is a widely used format |
| reuseRefreshTokens | true |  |
| refreshTokenTimeToLive | 12hours |  |
| idTokenSignatureAlgorithm | RSASSA-PKCS1-v1_5 using SHA-256 |  |

# Concepts

---

```
  +--------+                                           +---------------+
  |        |--(A)------- Authorization Grant --------->|               |
  |        |                                           |               |
  |        |<-(B)----------- Access Token -------------|               |
  |        |               & Refresh Token             |               |
  |        |                                           |               |
  |        |                            +----------+   |               |
  |        |--(C)---- Access Token ---->|          |   |               |
  |        |                            |          |   |               |
  |        |<-(D)- Protected Resource --| Resource |   | Authorization |
  | Client |                            |  Server  |   |     Server    |
  |        |--(E)---- Access Token ---->|          |   |               |
  |        |                            |          |   |               |
  |        |<-(F)- Invalid Token Error -|          |   |               |
  |        |                            +----------+   |               |
  |        |                                           |               |
  |        |--(G)----------- Refresh Token ----------->|               |
  |        |                                           |               |
  |        |<-(H)----------- Access Token -------------|               |
  +--------+           & Optional Refresh Token        +---------------+
reference - [https://datatracker.ietf.org/doc/html/rfc6749](https://datatracker.ietf.org/doc/html/rfc6749)
```

There is no refreshToken in this project. So the steps are only up to step F, and going to step F resets to step A.

## **How to use**

---

## **If you want to learn more about project**

---

- [https://datatracker.ietf.org/doc/html/rfc6749](https://datatracker.ietf.org/doc/html/rfc6749)
