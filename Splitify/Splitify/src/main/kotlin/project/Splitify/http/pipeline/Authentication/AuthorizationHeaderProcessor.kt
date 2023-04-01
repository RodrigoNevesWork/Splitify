package project.splitify.http.pipeline.authentication


import org.springframework.stereotype.Component
import project.splitify.domain.JWToken
import project.splitify.http.userController.User
import project.splitify.services.UserServices
import javax.servlet.http.Cookie

@Component
class AuthorizationHeaderProcessor(
    private val userServices: UserServices,
) {

    fun process(cookie : Cookie?) : User?{
        if(cookie == null) return null

        val value = cookie.value

        return userServices.getUserByToken(JWToken(value))
    }

}