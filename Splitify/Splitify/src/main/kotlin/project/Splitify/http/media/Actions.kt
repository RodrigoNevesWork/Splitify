package project.splitify.http.media

import project.splitify.http.media.siren.ActionModel
import java.util.UUID

object Actions {

    private const val GET = "GET"
    private const val POST = "POST"
    private const val DELETE = "DELETE"
    private const val PUT = "Put"

    val signup = ActionModel(
        name = Rels.SIGNUP,
        title = "Signup",
        method = POST,
        href = Uris.users()
    )

    fun deleteUser(userID : Int) = ActionModel(
        name = Rels.DELETE_USER,
        title = "Delete User",
        method = DELETE,
        href = Uris.userByID(userID)
    )

    fun addFriend(userID : Int) = ActionModel(
        name = Rels.ADD_FRIEND,
        title = "Add Friend",
        method = POST,
        href = Uris.userByID(userID)
    )

    fun acceptRequest(userID : Int,requestID : UUID) = ActionModel(
        name = Rels.FRIEND_REQUEST,
        title = "Accept Friend Request",
        method = PUT,
        href = Uris.friendsRequest(userID,requestID)
    )

    fun declineRequest(userID : Int,requestID : UUID) = ActionModel(
        name = Rels.FRIEND_REQUEST,
        title = "Decline Friend Request",
        method = PUT,
        href = Uris.friendsRequest(userID,requestID)
    )

    fun payPurchase(tripID : Int, purchaseID : String) = ActionModel(
        name = Rels.PAY_PURCHASE,
        title = "Pay Purchase",
        method = POST,
        href = Uris.purchase(tripID, purchaseID)
    )

    val searchUser = ActionModel(
        name = Rels.SEARCH_USER,
        title = "Search user",
        method = GET,
        href = Uris.searchUser()
    )

    fun userTrips(userID: Int) = ActionModel(
        name = Rels.USER_TRIPS,
        title = "Trips of User",
        method = GET,
        href = Uris.userTrips(userID)
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

    fun createPurchase(tripID: Int) = ActionModel(
        name = Rels.CREATE_PURCHASE,
        title = "Create Purchase",
        method = POST,
        href = Uris.createPurchase(tripID)
    )

    fun purchaseByID(tripID: Int,purchaseID : String) = ActionModel(
        name = Rels.PAY_PURCHASE,
        title = "Pay Purchase",
        method = POST,
        href = Uris.purchase(tripID,purchaseID)
    )



}