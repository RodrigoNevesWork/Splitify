package project.splitify.repositories

import project.splitify.domain.Purchase

interface PurchaseRepository {
    fun addPurchase(purchase: Purchase)
    fun checkBuyer(purchaseID : String, userID : Int) : Boolean
    fun checkIfHasAlreadyPayed(purchaseID: String, userID: Int) : Boolean
    fun payPurchase(purchaseID : String, payingUser : Int)
    fun getPurchaseInformation(purchaseID: String) : Purchase?
}