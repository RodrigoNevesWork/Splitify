package project.splitify.services

import org.springframework.stereotype.Component
import project.splitify.domain.*
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

    fun addUserToTrip(userIDToBeAdded: Int, tripID: Int, userID: Int) =
        transactionManager.run {

            if(!it.userRepository.checkIfIsInTrip(userID,tripID)) throw NotInThisTrip()

            if(it.userRepository.checkIfIsInTrip(userIDToBeAdded,tripID)) throw AlreadyInThisTrip()

           it.tripRepository.addUserToTrip(userIDToBeAdded, tripID)
        }
}


