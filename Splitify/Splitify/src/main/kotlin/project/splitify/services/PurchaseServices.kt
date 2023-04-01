package project.splitify.services

import org.springframework.stereotype.Service
import project.splitify.domain.*
import project.splitify.http.PurchaseControler.Purchase
import project.splitify.http.PurchaseControler.PurchaseCreation
import project.splitify.repositories.TransactionManager
import java.time.LocalDate
import java.util.*

@Service
class PurchaseServices(
    private val transactionManager : TransactionManager
) {

    fun createPurchase(purchaseCreation: PurchaseCreation, userID : Int, tripID : Int) : String{

        return transactionManager.run {

        if(!it.userRepository.isInTrip(userID,tripID)) throw NotInThisTrip()

        it.tripRepository.getTrip(tripID) ?: throw TripNotExists()

        val id = UUID.randomUUID().toString()

            it.purchaseRepository.addPurchase(
                Purchase(
                    id = id,
                    price = purchaseCreation.price,
                    description = purchaseCreation.description,
                    trip_id = tripID,
                    user_id = userID,
                    purchase_date = LocalDate.now()
                )
            )
             id
        }
    }

    fun payPurchase(purchaseID : String, userID: Int, payingUser : Int, tripID : Int){
        transactionManager.run {

            if(!it.userRepository.isInTrip(userID, tripID)) throw NotInThisTrip()
            if(!it.friendsManagementRepository.areFriends(userID, payingUser)) throw NotFriends()
            if(!it.purchaseRepository.checkBuyer(purchaseID,userID)) throw NotBuyer()
            if(it.purchaseRepository.checkIfHasAlreadyPayed(purchaseID,payingUser)) throw AlreadyPayed()

            it.purchaseRepository.payPurchase(purchaseID,payingUser)


        }
    }

    fun getPurchaseInformation(purchaseID : String, userID : Int, tripID: Int) : Purchase {
        return transactionManager.run {
            if(!it.userRepository.isInTrip(userID, tripID)) throw NotInThisTrip()
            val purchase = it.purchaseRepository.getPurchaseInformation(purchaseID) ?: throw PurchaseNotExists()
            val userTrips = it.userRepository.getTripsOfUser(userID)
            if(userTrips.trips.none { trip -> trip.id == purchase.trip_id  }) throw PurchaseNotInATripOfUser()
            purchase
        }
    }


}