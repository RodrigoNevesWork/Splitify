package project.splitify.http.media

import project.splitify.http.media.siren.ActionModel
import java.util.UUID

object Actions {

    private const val GET = "GET"
    private const val POST = "POST"
    private const val DELETE = "DELETE"

    val signup = ActionModel(
        name = Rels.SIGNUP,
        title = "Signup",
        method = POST,
        href = Uris.userSignup()
    )

    fun deleteUser(userID : Int) = ActionModel(
        name = Rels.DELETE_USER,
        title = "Delete User",
        method = DELETE,
        href = Uris.userByID(userID)
    )

    fun searchUser(userName : String) = ActionModel(
        name = Rels.SEARCH_USER,
        title = "Search user",
        method = GET,
        href = Uris.searchUser(userName)
    )

    val userTrips = ActionModel(
        name = Rels.USER_TRIPS,
        title = "Trips of User",
        method = GET,
        href = Uris.userTrips()
    )

    fun addUserToTrip(tripID : Int) = ActionModel(
        name = Rels.ADD_USER_TRIP,
        title = "Add User to Trip",
        method = POST,
        href = Uris.tripByID(tripID)
    )

    val createTrip = ActionModel(
        name = Rels.CREATE_TRIP,
        title = "Create trip",
        method = POST,
        href = Uris.createTrip()
    )

    fun tripInformation(tripID: Int) = ActionModel(
        name = Rels.TRIP_INFORMATION,
        title = "Trip Information",
        method = GET,
        href = Uris.tripByID(tripID)
    )

    val createPurchase = ActionModel(
        name = Rels.CREATE_PURCHASE,
        title = "Create Purchase",
        method = POST,
        href = Uris.createPurchase()
    )

    fun purchaseByID(purchaseID : UUID) = ActionModel(
        name = Rels.PAY_PURCHASE,
        title = "Pay Purchase",
        method = POST,
        href = Uris.purchase(purchaseID)
    )



}