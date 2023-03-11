package project.splitify.repositories

import project.splitify.domain.Purchase
import java.util.*

interface PurchaseRepository {
    fun addPurchase(purchase: Purchase)
    fun checkBuyer(purchaseID : UUID, userID : Int) : Boolean
    fun checkIfHasAlreadyPayed(purchaseID: UUID, userID: Int) : Boolean
    fun payPurchase(purchaseID : UUID, payingUser : Int)
}