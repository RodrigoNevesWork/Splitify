package project.Splitify.repositories

import project.Splitify.domain.Purchase
import project.Splitify.domain.Trip
import project.Splitify.domain.TripCreation
import project.Splitify.domain.TripPurchases

interface TripRepository {

    fun create(userID : Int, tripCreation: TripCreation) : Int
    fun getTrip(tripID : Int) : TripPurchases?
}