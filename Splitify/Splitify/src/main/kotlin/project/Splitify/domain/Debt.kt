package project.splitify.domain

import java.util.UUID

/**
 * @param purchase -> the purchase that was made and the user has not paid
 * @param debt -> value that the user must pay
 */
data class Debt(val purchase : Purchase, val debt : Float)