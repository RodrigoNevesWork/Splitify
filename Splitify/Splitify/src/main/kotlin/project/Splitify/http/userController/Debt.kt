package project.splitify.http.userController

import project.splitify.http.PurchaseControler.Purchase

/**
 * @param purchase -> the purchase that was made and the user has not paid
 * @param debt -> value that the user must pay
 */
data class Debt(val purchase : Purchase, val debt : Float)