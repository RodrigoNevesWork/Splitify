package project.splitify.repositories

import project.splitify.http.tripController.tripDTO.TripCreation
import project.splitify.http.tripController.tripDTO.TripPurchases

interface TripRepository {

    fun create(userID : Int, tripCreation: TripCreation) : Int
    fun getTrip(tripID : Int) : TripPurchases?
    fun addUserToTrip(userID : Int, tripID : Int)
}