package project.Splitify.http.pipeline


import org.springframework.stereotype.Component
import project.Splitify.domain.Token
import project.Splitify.domain.User
import project.Splitify.services.UserServices
import java.util.*
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