package project.Splitify.services

import org.springframework.stereotype.Component
import project.Splitify.domain.NotInThisTrip
import project.Splitify.domain.Purchase
import project.Splitify.domain.PurchaseCreation
import project.Splitify.domain.TripNotExists
import project.Splitify.repositories.TransactionManager
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