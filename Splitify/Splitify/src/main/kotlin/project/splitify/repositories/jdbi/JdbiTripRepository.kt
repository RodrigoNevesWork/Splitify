package project.splitify.repositories.jdbi

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import project.splitify.domain.Purchase
import project.splitify.domain.Trip
import project.splitify.domain.TripCreation
import project.splitify.domain.TripPurchases
import project.splitify.repositories.TripRepository

class JdbiTripRepository(
    private val handle : Handle
) : TripRepository {

    override fun create(userID: Int, tripCreation: TripCreation) :  Int {
            val id = handle
                     .createQuery("insert into dbo.trip (location) values(:location) returning id")
                     .bind("location", tripCreation.location)
                     .mapTo<Int>()
                     .single()

                    handle.createUpdate("insert into dbo.user_trip (user_id, trip_id) values (:userID, :tripID)")
                        .bind("userID",userID)
                        .bind("tripID",id)
                        .execute()
            return id
            }

    override fun getTrip(tripID : Int) : TripPurchases?{
             val trip = handle
                        .createQuery("select * from dbo.trip where id = :tripID")
                        .bind("tripID", tripID)
                        .mapTo<Trip>()
                        .singleOrNull() ?: return null

             val purchases = handle.createQuery("select * from dbo.purchase where trip_id = :tripID")
                        .bind("tripID", tripID)
                        .mapTo<Purchase>()
                        .toList()

        return TripPurchases(trip, purchases)
    }

    override fun addUserToTrip(userID: Int, tripID: Int) {
        handle
            .createUpdate("insert into dbo.user_trip(user_id, trip_id) values (:userID, :tripID)")
            .bind("userID", userID)
            .bind("tripID", tripID)
            .execute()
    }


}
