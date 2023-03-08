package project.Splitify.http

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.Splitify.domain.Token
import project.Splitify.domain.UserCreation
import project.Splitify.domain.UserOutput
import project.Splitify.services.UserServices
import java.util.*

@RestController
class UserController(
    private val userServices : UserServices
) {

    @PostMapping(Uris.User.SIGNUP)
    fun createUser(@RequestBody userCreation: UserCreation) : ResponseEntity<String> {
        val parts = userServices.createUser(userCreation)
        val responseHeaders = HttpHeaders()

        val cookieName = "token"
        val cookieValue = parts.second.token

        responseHeaders.add(HttpHeaders.SET_COOKIE, ResponseCookie.from(cookieName, cookieValue).build().toString())

        return ResponseEntity.status(201).headers(responseHeaders).body("User Created")

        }

    @GetMapping(Uris.User.BY_EMAIL)
    fun getUserByEmail(@PathVariable user_email: String) : ResponseEntity<UserOutput>{
        val user = userServices.getUserByEmail(user_email)
        return ResponseEntity.status(200).body(user)
    }




}