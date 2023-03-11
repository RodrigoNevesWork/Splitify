package project.splitify.http

import org.apache.coyote.Response
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.splitify.domain.Trips
import project.splitify.domain.User
import project.splitify.domain.UserCreation
import project.splitify.domain.UserOutput
import project.splitify.http.pipeline.Authentication
import project.splitify.services.UserServices
import java.util.*

@RestController
class UserController(
    private val userServices : UserServices,
) {

    @PostMapping(Uris.User.SIGNUP)
    fun createUser(@RequestBody userCreation: UserCreation) : ResponseEntity<String> {
        val parts = userServices.createUser(userCreation)
        val responseHeaders = HttpHeaders()

        val cookieName = "token"
        val cookieValue = parts.second.token

        responseHeaders.add(HttpHeaders.SET_COOKIE, ResponseCookie.from(cookieName, cookieValue).build().toString())

        return ResponseEntity.status(201).headers(responseHeaders).body("User Created with token ${parts.second} and id ${parts.first}")

        }

    @Authentication
    @GetMapping(Uris.User.BY_EMAIL)
    fun getUserByEmail(@PathVariable user_email: String) : ResponseEntity<UserOutput>{
        val user = userServices.getUserByEmail(user_email)
        return ResponseEntity.ok(user)
    }

    @Authentication
    @GetMapping(Uris.User.TRIPS)
    fun getTripsOfUser(user : User) : ResponseEntity<Trips>{
        val trips = userServices.getTripsOfUser(user.id)
        return ResponseEntity.ok(trips)
    }




}