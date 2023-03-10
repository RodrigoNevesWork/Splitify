package project.splitify.http.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import project.splitify.domain.JWToken
import javax.crypto.spec.SecretKeySpec

@Component
class JwtUtils(jwtConfiguration: JwtConfiguration) {

    private val acessTokenKey = SecretKeySpec(
            jwtConfiguration.accessTokenSecret.toByteArray(),
            SECRET_KEY_ALGORITHM
    )

    data class JwtPayload(val claims: Claims) {

        val userPhone: String = claims[USER_CREDENTIALS] as String

        companion object {

            fun createJwtPayload(userPhone : String): JwtPayload {
                val claims = Jwts.claims()
                claims[USER_CREDENTIALS] = userPhone
                return JwtPayload(claims)
            }

            private const val USER_CREDENTIALS = "userphone"
        }
    }

    fun createAccessToken(jwtPayload: JwtPayload): JWToken {

        return JWToken(
             token =   Jwts.builder()
                       .setClaims(jwtPayload.claims)
                       .signWith(acessTokenKey)
                       .compact()
        )
    }


    companion object {
        private const val SECRET_KEY_ALGORITHM = "HmacSHA512"

    }
}