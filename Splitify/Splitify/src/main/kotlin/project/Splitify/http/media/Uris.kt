package project.splitify.http.media

import org.springframework.web.util.UriTemplate
import java.net.URI
import java.util.UUID

object Uris {

    const val HOME = "/"

    object Users{
        const val USER_HOME = "/users/home"
        const val USERS = "/users"
        const val LOGIN = "/users/login"
        const val LOGOUT = "/users/logout"
        const val SEARCH_USER = "users/search"
        const val BY_ID = "users/{user_id}"
        const val TRIPS = "users/{user_id}/trips"
        const val FRIEND_REQUEST = "users/{user_id}/friend_request/{friend_request_id}"
        const val LIST_OF_FRIENDS = "users/{user_id}/friend_list"
        const val LIST_OF_REQUESTS = "users/{user_id}/friend_requests"
        const val DEBTS = "users/{user_id}/trips/{trip_id}/debts"
        const val PAYMENTS = "users/{user_id}/payments"
        const val DEBTORS = "users/{user_id}/trips/{trip_id}/debtors"
    }

    object Trips{
        const val CREATE = "/trips"
        const val TRIP_BY_ID = "/trips/{trip_id}"
        const val USERS_IN_TRIP = "/trips/{trip_id}/users"

    }

    object Purchases{
        const val CREATE = "trips/{trip_id}/purchases"
        const val PURCHASE = "trips/{trip_id}/purchases/{purchase_id}"
    }

    fun home(): URI = URI(HOME)

    fun userHome(): URI = URI(Users.USER_HOME)
    fun users() : URI = URI(Users.USERS)
    fun searchUser() : URI = URI(Users.SEARCH_USER)
    fun userByID(userID : Int) : URI = UriTemplate(Users.BY_ID).expand(userID)

    fun friendsRequest(userID : Int,id : UUID) : URI = UriTemplate(Users.FRIEND_REQUEST).expand(userID,id)

    fun userTrips(userID: Int) : URI = UriTemplate(Users.TRIPS).expand(userID)
    fun createTrip() : URI = URI(Trips.CREATE)
    fun tripByID(tripID : Int) : URI = UriTemplate(Trips.TRIP_BY_ID).expand(tripID)

    fun createPurchase(tripID : Int) : URI = UriTemplate(Purchases.CREATE).expand(tripID)
    fun purchase(tripID : Int,purchaseID : String) : URI = UriTemplate(Purchases.PURCHASE).expand(tripID,purchaseID)


}