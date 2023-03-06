package project.Splitify.http.pipeline


import org.springframework.stereotype.Component
import project.Splitify.domain.User
import project.Splitify.services.UserServices
import java.util.*

@Component
class AuthorizationHeaderProcessor(
    val userServices: UserServices,
) {

    fun process(credentials : String?) : User?{
        if(credentials == null) return null

        val parts = credentials.trim().split(" ")

        if(parts.size != 2 || parts[0].lowercase() != SCHEMA) return null

        return userServices.getUserByToken(parts[1])
    }
    companion object{
        const val SCHEMA = "bearer"
    }







}