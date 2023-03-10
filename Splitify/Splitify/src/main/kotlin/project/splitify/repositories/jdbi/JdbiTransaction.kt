package project.splitify.repositories.jdbi

import org.jdbi.v3.core.Handle
import project.splitify.repositories.PurchaseRepository
import project.splitify.repositories.Transaction
import project.splitify.repositories.TripRepository
import project.splitify.repositories.UserRepository

class JdbiTransaction(
    private val handle: Handle
) : Transaction {

    override val userRepository: UserRepository by lazy { JbdiUserRepository(handle) }

    override val tripRepository: TripRepository by lazy { JdbiTripRepository(handle) }

    override val purchaseRepository: PurchaseRepository by lazy {JdbiPurchaseRepository(handle)}

    override fun rollback() {
        handle.rollback()
    }
}