package project.splitify.repositories

import project.splitify.http.tripController.TripCreation
import project.splitify.http.tripController.TripPurchases

interface TripRepository {

    fun create(userID : Int, tripCreation: TripCreation) : Int
    fun getTrip(tripID : Int) : TripPurchases?
    fun addUserToTrip(userID : Int, tripID : Int)
}