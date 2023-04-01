package project.splitify.services

import org.springframework.stereotype.Service
import project.splitify.domain.*
import project.splitify.http.tripController.TripCreation
import project.splitify.http.tripController.TripPurchases
import project.splitify.repositories.TransactionManager

@Service
class TripServices(
    private val transactionManager : TransactionManager
){
    fun createTrip(userID : Int,tripCreation : TripCreation) =
        transactionManager.run {
            it.tripRepository.create(userID,tripCreation)
        }

    fun getTrip(tripID : Int, userID : Int) : TripPurchases =
        transactionManager.run {
            if(!it.userRepository.isInTrip(userID, tripID)) throw NotInThisTrip()
            it.tripRepository.getTrip(tripID) ?: throw TripNotExists()
        }

    fun addUserToTrip(userIDToBeAdded: Int, tripID: Int, userID: Int) =
        transactionManager.run {

            if(!it.friendsManagementRepository.areFriends(userIDToBeAdded, userID)) throw NotFriends()

            if(!it.userRepository.isInTrip(userID,tripID)) throw NotInThisTrip()

            if(it.userRepository.isInTrip(userIDToBeAdded,tripID)) throw AlreadyInThisTrip()

            it.tripRepository.addUserToTrip(userIDToBeAdded, tripID)
        }

}


