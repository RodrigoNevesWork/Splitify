package project.splitify.services

import org.springframework.stereotype.Component
import project.splitify.domain.NotInThisTrip
import project.splitify.domain.Purchase
import project.splitify.domain.PurchaseCreation
import project.splitify.domain.TripNotExists
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


}