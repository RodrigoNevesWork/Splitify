package project.splitify.services

import org.springframework.stereotype.Component
import project.splitify.domain.*
import project.splitify.repositories.TransactionManager
import java.util.*

@Component
class PurchaseServices(
    private val transactionManager : TransactionManager
) {

    fun createPurchase(purchaseCreation: PurchaseCreation, userID : Int, tripID : Int) : UUID{
        return transactionManager.run {

         it.tripRepository.getTrip(tripID) ?: throw TripNotExists()

        if(!it.userRepository.checkIfIsInTrip(userID,tripID)) throw NotInThisTrip()

        val id = UUID.randomUUID()

            it.purchaseRepository.addPurchase(
                Purchase(
                    id = id,
                    price = purchaseCreation.price,
                    description = purchaseCreation.description,
                    trip_id = tripID,
                    user_id = userID
                )
            )

             id
        }

    }

    fun payPurchase(purchaseID : UUID, userID: Int, payingUser : Int){
        transactionManager.run {

            if(!it.purchaseRepository.checkBuyer(purchaseID,userID)) throw NotBuyer()
            if(it.purchaseRepository.checkIfHasAlreadyPayed(purchaseID,payingUser)) throw AlreadyPayed()

            it.purchaseRepository.payPurchase(purchaseID,payingUser)


        }
    }


}