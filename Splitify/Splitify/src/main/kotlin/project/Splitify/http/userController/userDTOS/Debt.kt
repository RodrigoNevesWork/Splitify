package project.splitify.http.userController.userDTOS

import project.splitify.http.purchaseControler.purchaseDTO.Purchase

/**
 * @param purchase -> the purchase that was made and the user has not paid
 * @param debt -> value that the user must pay
 */
data class Debt(val purchase : Purchase, val debt : Float)