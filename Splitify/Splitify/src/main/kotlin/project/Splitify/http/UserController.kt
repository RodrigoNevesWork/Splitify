package project.Splitify.http

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.Splitify.domain.Token
import project.Splitify.domain.User
import project.Splitify.domain.UserCreation
import project.Splitify.services.UserServices
import java.util.*

@RestController
class UserController(
    private val userServices : UserServices
) {

    @PostMapping(Uris.User.SIGNUP)
    fun createUser(@RequestBody userCreation: UserCreation) : ResponseEntity<Pair<Int,Token>> {
        val parts = userServices.createUser(userCreation)
        return ResponseEntity.status(201).body(parts)
        }


}