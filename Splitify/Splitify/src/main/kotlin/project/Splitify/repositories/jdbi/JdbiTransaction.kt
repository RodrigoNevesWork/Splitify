package project.Splitify.repositories.jdbi

import org.jdbi.v3.core.Handle
import project.Splitify.repositories.PurchaseRepository
import project.Splitify.repositories.Transaction
import project.Splitify.repositories.TripRepository
import project.Splitify.repositories.UserRepository

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