package project.splitify.services

import org.springframework.stereotype.Component
import project.splitify.domain.TripCreation
import project.splitify.domain.TripNotExists
import project.splitify.domain.TripPurchases
import project.splitify.repositories.TransactionManager

@Component
class TripServices(
    private val transactionManager : TransactionManager
){
    fun createTrip(userID : Int,tripCreation : TripCreation) =
        transactionManager.run {
            it.tripRepository.create(userID,tripCreation)
        }

    fun getTrip(tripID : Int) : TripPurchases =
        transactionManager.run {
            it.tripRepository.getTrip(tripID) ?: throw TripNotExists()
        }

    }


