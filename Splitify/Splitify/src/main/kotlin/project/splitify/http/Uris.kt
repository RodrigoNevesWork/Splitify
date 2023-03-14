package project.splitify.http

object Uris {

    object Users{
        const val SIGNUP = "/users"
        const val BY_EMAIL = "user/{user_email}"
        const val BY_ID = "user/{user_id}"
        const val TRIPS = "user/trips"
    }

    object Trips{
        const val CREATE = "/trips"
        const val TRIP_BY_ID = "/trips/{trip_id}"

    }

    object Purchases{
        const val CREATE = "/{trip_id}/purchases"
        const val PURCHASE = "/purchases/{purchase_id}"
    }


}