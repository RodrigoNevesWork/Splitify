package project.splitify.http.pipeline


import org.springframework.stereotype.Component
import project.splitify.domain.Token
import project.splitify.domain.User
import project.splitify.services.UserServices
import javax.servlet.http.Cookie

@Component
class AuthorizationHeaderProcessor(
    val userServices: UserServices,
) {

    fun process(cookie : Cookie?) : User?{
        if(cookie == null) return null

        val value = cookie.value

        return userServices.getUserByToken(Token(value))
    }
    companion object{
        const val SCHEMA = "bearer"
    }

}