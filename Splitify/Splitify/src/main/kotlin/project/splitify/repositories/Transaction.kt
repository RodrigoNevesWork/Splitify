package project.splitify.repositories

interface Transaction {

    val userRepository: UserRepository

    val tripRepository: TripRepository

    val purchaseRepository : PurchaseRepository

    val friendsManagementRepository : FriendsManagementRepository

    fun rollback() // for testing methods
}