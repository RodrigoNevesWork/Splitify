package project.Splitify.repositories

import project.Splitify.domain.Purchase

interface PurchaseRepository {
    fun addPurchase(purchase: Purchase)
}