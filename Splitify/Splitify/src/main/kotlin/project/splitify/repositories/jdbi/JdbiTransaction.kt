package project.splitify.repositories.jdbi

import org.jdbi.v3.core.Handle
import project.splitify.repositories.*

class JdbiTransaction(
    private val handle: Handle
) : Transaction {

    override val userRepository: UserRepository by lazy { JbdiUserRepository(handle) }

    override val tripRepository: TripRepository by lazy { JdbiTripRepository(handle) }

    override val purchaseRepository: PurchaseRepository by lazy {JdbiPurchaseRepository(handle)}

    override val friendsManagementRepository: FriendsManagementRepository by lazy { JdbiFriendsManagementRepository(handle)  }


    override fun rollback() {
        handle.rollback()
    }
}