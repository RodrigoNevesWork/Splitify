package project.splitify.http.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import project.splitify.domain.JWToken
import project.splitify.http.userController.userDTOS.UserCreation
import javax.crypto.spec.SecretKeySpec

@Component
class JwtUtils(jwtConfiguration: JwtConfiguration) {

    private val acessTokenKey = SecretKeySpec(
            jwtConfiguration.accessTokenSecret.toByteArray(),
            SECRET_KEY_ALGORITHM
    )

    data class JwtPayload(val claims: Claims) {

        companion object {

            fun createJwtPayload(userCreation: UserCreation): JwtPayload {
                val claims = Jwts.claims()
                claims[USER_CREATION] = userCreation
                return JwtPayload(claims)
            }

            private const val USER_CREATION = "userCreation"
        }
    }

    fun createJWToken(jwtPayload: JwtPayload): JWToken {

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