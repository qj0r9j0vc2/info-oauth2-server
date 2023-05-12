# **info-oauth2-server**
---
## 연동 방법: [백엔드와 프론트가 분리된 개발 환경에서 적용 방법(필독)](https://dsm-info.notion.site/299d6088aa804f2995a13b6af5a9b05a)

> info-oauth2 는 [info-dsm](https://github.com/info-dsm/INFO_v2_Backend)에서 제공하는 유저 데이터를 기반으로 OAuth2 서비스를 제공합니다.
> 

# 개념

---

![image](https://user-images.githubusercontent.com/59428479/236992971-76a8b93c-5ba3-4280-84c2-54b1b821d0d8.png)

2023-05-09기준 해당 서비스는 authorization Code Grant 방식(optional PKCE)으로만 작동하며, 위 자료는 해당 방식의 인증 과정을 표현한 내용입니다.

## AuthorizationCodeGrant 작동 과정

---

1. 어플리케이션이 OAuth 서버에 요청해 브라우저를 열어 사용자가 인증을 진행하게 한다.
2. 사용자는 브라우저에서 나오는 인증 프롬프트로 인증 후 어플리케이션의 요청을 승인한다.
3. 사용자는 OAuth 서버에서 인증코드를 받아 어플리케이션으로 돌아온다.
4. 어플리케이션은 인증 코드를 access token으로 교환해준다.

# 사용법

---

[info oauth2 authorization server(oauth2.info-dsm.info)](https://documenter.getpostman.com/view/15222398/2s93eYWYk4)

# 자주하는 질문

---

[PKCE란?](https://www.notion.so/PKCE-8f75c3da43ce40c29b86e64604368d84)

[백엔드와 프론트가 분리된 개발 환경에서 적용 방법](https://www.notion.so/299d6088aa804f2995a13b6af5a9b05a)

# 파라미터

| authorizationCodeTimeToLive | 5minutes | 설명 |
| --- | --- | --- |
| accessTokenTimeToLive | 1hours | accessToken의 만료 시간을 1시간으로 설정합니다. |
| accessTokenFormat | self-contained | self-contained 지정 시 사용자의 토큰 정보를 포함하는 보호된 데이터 구조를 사용하며 주로 JWT를 이용해 구현합니다. |
| reuseRefreshTokens | true | refreshToken이 존재합니다. |
| refreshTokenTimeToLive | 12hours | refreshToken의 만료 시간을 12시간으로 설정합니다. |
| idTokenSignatureAlgorithm | RSASSA-PKCS1-v1_5 using SHA-256 |  |

## 프로젝트에 대한 자세한 설명

---

- [https://datatracker.ietf.org/doc/html/rfc6749](https://datatracker.ietf.org/doc/html/rfc6749)
    - `**The OAuth 2.0 Authorization Framework**` 문서
- [https://cheese10yun.github.io/spring-oauth2-provider/](https://cheese10yun.github.io/spring-oauth2-provider/)
    - 승인 종류 방식에 대한 설명. info oauth2에서는 authorization_code와 authorization_code with PKCE를 지원합니다.
