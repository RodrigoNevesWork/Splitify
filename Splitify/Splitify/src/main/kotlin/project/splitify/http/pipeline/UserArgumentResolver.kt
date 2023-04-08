package project.splitify.http.pipeline


import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import project.splitify.domain.Unauthorized
import project.splitify.http.userController.userDTOS.User
import javax.servlet.http.HttpServletRequest

@Component
class UserArgumentResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean = parameter.parameterType == User::class.java

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val request = webRequest.getNativeRequest(HttpServletRequest::class.java)
            ?: throw Unauthorized()
        return getUserFrom(request)
    }

    companion object{
        private const val KEY = "UserArgumentResolver"

        fun addUserTo(user: User, request: HttpServletRequest) {
            return request.setAttribute(KEY, user)
        }

        fun getUserFrom(request: HttpServletRequest): User {
            val player = request.getAttribute(KEY)?.let {
                it as User
            }
            return player?: throw Unauthorized()
        }
    }

}