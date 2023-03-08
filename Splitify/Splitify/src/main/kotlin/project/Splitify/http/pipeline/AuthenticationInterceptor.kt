package project.Splitify.http.pipeline

import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import project.Splitify.domain.User
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthenticationInterceptor(
    private val authorizationHeaderProcessor : AuthorizationHeaderProcessor
)  : HandlerInterceptor{
    private val NAME_AUTHORIZATION_HEADER = "Authorization"
    private val NAME_WWW_AUTHENTICATE_HEADER = "WWW-Authenticate"

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean{
        if(handler is HandlerMethod && handler.methodParameters.any{ it.parameterType == User::class.java }){

            val cookies = request.cookies
            val tokenCookie = cookies.find { it.name == "token" }

            val user = authorizationHeaderProcessor.process(tokenCookie)

            return if(user == null){
                response.status = 401
                response.addHeader(NAME_WWW_AUTHENTICATE_HEADER, AuthorizationHeaderProcessor.SCHEMA)
                false
            }else{
                UserArgumentResolver.addUserTo(user,request)
                true
            }

        }
        return true
    }
}