package project.splitify.repositories

import project.splitify.domain.TripCreation
import project.splitify.domain.TripPurchases

interface TripRepository {

    fun create(userID : Int, tripCreation: TripCreation) : Int
    fun getTrip(tripID : Int) : TripPurchases?
    fun addUserToTrip(userID : Int, tripID : Int)
}