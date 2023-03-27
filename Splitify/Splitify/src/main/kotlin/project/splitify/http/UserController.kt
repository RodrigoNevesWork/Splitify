package project.splitify.http

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.splitify.domain.*
import project.splitify.http.media.Links
import project.splitify.http.media.Problem.Companion.PROBLEM_MEDIA_TYPE
import project.splitify.http.media.Rels
import project.splitify.http.media.Uris
import project.splitify.http.media.siren.SirenModel
import project.splitify.http.media.siren.SirenModel.Companion.SIREN_MEDIA_TYPE
import project.splitify.http.pipeline.authentication.Authentication
import project.splitify.services.FriendManagementServices
import project.splitify.services.UserServices
import java.util.*
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping(produces = [SIREN_MEDIA_TYPE,PROBLEM_MEDIA_TYPE])
class UserController(
    private val userServices : UserServices,
    private val friendManagementServices : FriendManagementServices
) {

    @PostMapping(Uris.Users.SIGNUP)
    fun createUser(@RequestBody userCreation: UserCreation,response: HttpServletResponse) : ResponseEntity<String> {

        val parts = userServices.createUser(userCreation)

        addAuthenticationCookies(response,parts.second)

        return ResponseEntity.status(201).body(parts.toString())

    }

    @Authentication
    @DeleteMapping(Uris.Users.BY_ID)
    fun deleteUser(@PathVariable user_id: Int,user : User) : ResponseEntity<Unit>{
        userServices.deleteUser(user_id, user.id)
        return ResponseEntity.ok().build()
    }


    @Authentication
    @GetMapping(Uris.Users.SEARCH_USER)
    fun searchUserByUsername(@PathVariable user_name: String) :  ResponseEntity<ListOfUsers>{
        val users = userServices.getUserByName(user_name)
        return ResponseEntity.ok().body(users)
    }

    @Authentication
    @GetMapping(Uris.Users.TRIPS)
    fun getTripsOfUser(user : User, @PathVariable user_id : Int) : ResponseEntity<Trips>{
        if(user.id != user_id) throw Unauthorized()
        val trips = userServices.getTripsOfUser(user_id)
        return ResponseEntity.ok(trips)
    }

    @Authentication
    @PostMapping(Uris.Users.BY_ID)
    fun makeFriendRequest(user : User, @PathVariable user_id: Int) : ResponseEntity<String>{
        if(user.id == user_id) throw FriendYourself()
        val id = friendManagementServices.makeFriendRequest(user_id, user.id)
        return ResponseEntity.status(201).body(id)
    }

    @Authentication
    @PutMapping(Uris.Users.FRIEND_REQUEST)
    fun decideFriendRequest(@PathVariable friend_request_id: String, @RequestBody decision: FriendRequestDecision, user : User, @PathVariable user_id: Int
    ) : ResponseEntity<Unit>{
        if(user.id != user_id) throw Unauthorized()
        friendManagementServices.decisionAboutRequest(decision,friend_request_id)
        return ResponseEntity.ok().build()
    }

    @Authentication
    @GetMapping(Uris.Users.LIST_OF_FRIENDS)
    fun getFriendList(user : User, @PathVariable user_id: Int) : ResponseEntity<List<Friend>>{
        val friendsList =  friendManagementServices.getFriendsList(user_id,user.id)
        return ResponseEntity.ok(friendsList)
    }

    @Authentication
    @GetMapping(Uris.Users.LIST_OF_REQUESTS)
    fun getFriendsRequests(user : User, @PathVariable user_id: Int) : ResponseEntity< List<FriendRequest>>{
       if(user.id != user_id) throw Unauthorized()
        val friendsRequest = friendManagementServices.getFriendsRequest(user_id)
        return ResponseEntity.ok(friendsRequest)
    }

    @Authentication
    @GetMapping(Uris.Users.DEBTS)
    fun getDebtsOfUser(@PathVariable user_id : Int, @PathVariable trip_id : Int,user: User) : ResponseEntity<List<Debt>>{
        if(user.id != user_id) throw Unauthorized()
        val debts = userServices.getDebtsOfUserInTrip(user_id,trip_id)
        return ResponseEntity.ok(debts)
    }

    @Authentication
    @GetMapping(Uris.Users.DEBTORS)
    fun getDebtersInTrip(@PathVariable trip_id: Int, @PathVariable user_id: Int, user : User):ResponseEntity<*>{
        if(user.id != user_id) throw Unauthorized()
        val debtors = userServices.getDebtorsInTrip(user_id,trip_id)
        return ResponseEntity.ok(debtors)
    }

    companion object{

        private fun addAuthenticationCookies(response: HttpServletResponse,jwToken: JWToken){
            val responseCookie = ResponseCookie.from("token", jwToken.token)
                .httpOnly(true)
                .path("/")
                .sameSite("Strict")
                .build()

            response.addCookie(responseCookie)

        }

        private fun HttpServletResponse.addCookie(cookie: ResponseCookie) {
            this.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())
        }
    }

}