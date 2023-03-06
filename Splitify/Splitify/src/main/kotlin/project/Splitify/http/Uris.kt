package project.Splitify.http

object Uris {

    object User{
        const val SIGNUP = "/users"
    }

    object Trip{
        const val CREATE = "/trips"
        const val TRIP_BY_ID = "/trip/{id}"
        const val EXPENSES_BY_USER_IN_TRIP = "/trip/{trip_id}/user/{user_ID}"

    }


}