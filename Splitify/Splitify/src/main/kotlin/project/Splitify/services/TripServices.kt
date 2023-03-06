package project.Splitify.services

import org.springframework.stereotype.Component
import project.Splitify.domain.PurchaseCreation
import project.Splitify.domain.TripCreation
import project.Splitify.domain.TripNotExists
import project.Splitify.domain.TripPurchases
import project.Splitify.repositories.TransactionManager

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


