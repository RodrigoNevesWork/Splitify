package project.splitify.http

object Uris {

    object User{
        const val SIGNUP = "/users"
        const val BY_EMAIL = "user/{user_email}"
        const val BY_ID = "user/{user_id}"
        const val TRIPS = "user/trips"
    }

    object Trip{
        const val CREATE = "/trips"
        const val TRIP_BY_ID = "/trip/{id}"
        const val USER_IN_TRIP = "/trip/{trip_id}/user/{user_ID}"

    }

    object Purchase{
        const val CREATE = "/{trip_id}/purchases"
        const val PURCHASE = "/purchase/{purchase_id}"
    }


}