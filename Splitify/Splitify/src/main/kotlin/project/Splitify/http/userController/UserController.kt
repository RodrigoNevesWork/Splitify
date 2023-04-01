package project.splitify.http.userController

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.web.bind.annotation.*
import project.splitify.domain.*
import project.splitify.http.userController.*
import project.splitify.http.media.Actions
import project.splitify.http.media.Links
import project.splitify.http.media.Problem.Companion.PROBLEM_MEDIA_TYPE
import project.splitify.http.media.Rels
import project.splitify.http.media.Uris
import project.splitify.http.media.siren.SirenModel
import project.splitify.http.media.siren.SirenModel.Companion.SIREN_MEDIA_TYPE
import project.splitify.http.media.siren.SubEntity
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
){

    @PostMapping(Uris.Users.USERS)
    fun createUser(@RequestBody userCreation: UserCreation, response: HttpServletResponse) : SirenModel<String> {

        val parts = userServices.createUser(userCreation)

        addAuthenticationCookies(response,parts.second)

        return SirenModel(
            clazz = listOf(Rels.SIGNUP),
            properties = parts.toString(),
            links = listOf(
                Links.home,
                Links.userHome
            ),
            actions = listOf(
                Actions.createTrip
            )
        )
    }

    @PostMapping(Uris.Users.LOGIN)
    fun login(loginModel: LoginModel, response : HttpServletResponse) : SirenModel<LoginOutput>{
        val credentials = userServices.login(loginModel)
        addAuthenticationCookies(response,credentials.token)
        return SirenModel(
            clazz = listOf(Rels.LOGIN),
            properties = credentials,
            links =  listOf(
                Links.home,
                Links.userHome
            ),
            actions = listOf(
                Actions.createTrip,
                Actions.userTrips(credentials.id)
            )
        )
    }

    @Authentication
    @PostMapping(Uris.Users.LOGOUT)
    fun logout(response: HttpServletResponse) : SirenModel<String>{
        clearAuthenticationCookies(response)
        return SirenModel(
            properties = "Logout",
            links = listOf(
                Links.home
            )
        )
    }

    @Authentication
    @DeleteMapping(Uris.Users.BY_ID)
    fun deleteUser(@PathVariable user_id: Int,user : User) : SirenModel<Unit>{
        if(user.id != user_id) throw Unauthorized()
        userServices.deleteUser(user_id, user.id)
        return SirenModel(
            links = listOf(
                Links.home
            )
        )
    }


    @Authentication
    @GetMapping(Uris.Users.SEARCH_USER)
    fun searchUserByUsername(@RequestParam(required = true) user_name : String) :  SirenModel<Int>{
        val users = userServices.getUserByName(user_name)
        return SirenModel(
            clazz = listOf(Rels.SEARCH_USER),
            properties = users.users.count(),
            links = listOf(
                Links.self(Uris.users()),
                Links.home,
                Links.userHome
            ),
            entities = users.users.map { user ->
                SubEntity.EmbeddedSubEntity(
                    rel = listOf(Rels.ITEM, Rels.USER),
                    properties = user,
                    links = listOf(
                        Links.self(Uris.searchUser()),
                        Links.home,
                        Links.userHome,
                    ),
                    actions = listOf(
                        Actions.addFriend(user.id)
                    )
                )
            }
        )
    }

    @Authentication
    @GetMapping(Uris.Users.TRIPS)
    fun getTripsOfUser(user : User, @PathVariable user_id : Int) : SirenModel<Int>{
        if(user.id != user_id) throw Unauthorized()
        val trips = userServices.getTripsOfUser(user_id)
        return SirenModel(
            clazz = listOf(Rels.USER_TRIPS),
            properties = trips.trips.count(),
            links = listOf(
                Links.self(Uris.userTrips(user_id)),
                Links.home,
                Links.userHome
            ),
            entities = trips.trips.map { trip ->
                SubEntity.EmbeddedSubEntity(
                    rel = listOf(Rels.ITEM,Rels.USER_TRIPS),
                    properties = trip,
                    links = listOf(
                        Links.self(Uris.tripByID(trip.id)),
                        Links.home,
                        Links.userHome
                    ),
                    actions = listOf(
                        Actions.createPurchase(trip.id),
                        Actions.addUserToTrip(trip.id)
                    )
                )
            }
        )
    }

    @Authentication
    @PostMapping(Uris.Users.BY_ID)
    fun makeFriendRequest(user : User, @PathVariable user_id: Int) : SirenModel<String>{
        if(user.id == user_id) throw FriendYourself()
        val id = friendManagementServices.makeFriendRequest(user_id, user.id)
        return SirenModel(
            clazz = listOf(Rels.ADD_USER),
            properties = id,
            links = listOf(
                Links.home,
                Links.userHome
            )
        )
    }

    @Authentication
    @PutMapping(Uris.Users.FRIEND_REQUEST)
    fun decideFriendRequest(@PathVariable friend_request_id: String, @RequestBody decision: FriendRequestDecision, user : User, @PathVariable user_id: Int
    ) : SirenModel<Unit>{
        if(user.id != user_id) throw Unauthorized()
        friendManagementServices.decisionAboutRequest(decision,friend_request_id)
        return SirenModel(
            links = listOf(
                Links.home,
                Links.userHome
            )
        )
    }

    @Authentication
    @GetMapping(Uris.Users.LIST_OF_FRIENDS)
    fun getFriendList(user : User, @PathVariable user_id: Int) : SirenModel<Int>{
        val friendsList = friendManagementServices.getFriendsList(user_id,user.id)
        return SirenModel(
            clazz = listOf(Rels.FRIEND_LIST),
            properties = friendsList.size,
            links = listOf(
                Links.home,Links.userHome
            ),
            entities = friendsList.map { friend ->
                SubEntity.EmbeddedSubEntity(
                    rel = listOf(Rels.ITEM, Rels.FRIEND),
                    properties = friend,
                    links = listOf(
                        Links.self(Uris.userByID(friend.id))
                    )
                )
            }
        )
    }

    @Authentication
    @GetMapping(Uris.Users.LIST_OF_REQUESTS)
    fun getFriendsRequests(user : User, @PathVariable user_id: Int) : SirenModel<Int>{
       if(user.id != user_id) throw Unauthorized()
        val friendsRequest = friendManagementServices.getFriendsRequest(user_id)
        return SirenModel(
            clazz = listOf(Rels.FRIEND_REQUEST),
            properties = friendsRequest.size,
            links = listOf(
                Links.home,Links.userHome
            ),
            entities = friendsRequest.map { friendRequest ->
                SubEntity.EmbeddedSubEntity(
                    rel = listOf(Rels.ITEM, Rels.FRIEND_REQUEST),
                    properties = friendRequest,
                    links = listOf(Links.self(Uris.friendsRequest(user_id,friendRequest.requestID))),
                    actions = listOf(
                        Actions.acceptRequest(user_id, friendRequest.requestID),
                        Actions.declineRequest(user_id,friendRequest.requestID)
                    )
                )
            }
        )
    }

    @Authentication
    @GetMapping(Uris.Users.DEBTS)
    fun getDebtsOfUser(@PathVariable user_id : Int, @PathVariable trip_id : Int,user: User) : SirenModel<Int>{
        if(user.id != user_id) throw Unauthorized()
        val debts = userServices.getDebtsOfUserInTrip(user_id,trip_id)
        return SirenModel(
            clazz = listOf(Rels.LIST_DEBTS),
            properties = debts.size,
            links = listOf(
                Links.home,
                Links.userHome
            ),
            entities = debts.map { debt ->
                SubEntity.EmbeddedSubEntity(
                    rel = listOf(Rels.DEBT),
                    properties = debt,
                    links = listOf(
                        Links.self(Uris.purchase(trip_id,debt.purchase.id))
                    )
                )
            }
        )
    }

    @Authentication
    @GetMapping(Uris.Users.DEBTORS)
    fun getDebtersInTrip(@PathVariable trip_id: Int, @PathVariable user_id: Int, user : User) : SirenModel<Int>{
        if(user.id != user_id) throw Unauthorized()
        val debtors = userServices.getDebtorsInTrip(user_id,trip_id)
        return SirenModel(
            clazz = listOf(Rels.LIST_DEBTORS),
            properties = debtors.size,
            links = listOf(Links.userHome,Links.home),
            entities = debtors.map { debtor ->
                SubEntity.EmbeddedSubEntity(
                    rel = listOf(Rels.DEBT),
                    properties = debtor,
                    links = listOf(
                        Links.self(Uris.purchase(trip_id,debtor.purchaseID))
                    ),
                    actions = listOf(
                        Actions.payPurchase(trip_id,debtor.purchaseID)
                    )
                )
            }
        )
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

        private fun clearAuthenticationCookies(response: HttpServletResponse){
            val tokenCookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .maxAge(0)
                .sameSite("Strict")
                .build()
        }

        private fun HttpServletResponse.addCookie(cookie: ResponseCookie) {
            this.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())
        }
    }

}