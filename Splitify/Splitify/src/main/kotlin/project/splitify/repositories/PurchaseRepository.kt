package project.splitify.repositories

import project.splitify.domain.Purchase

interface PurchaseRepository {
    fun addPurchase(purchase: Purchase)
}