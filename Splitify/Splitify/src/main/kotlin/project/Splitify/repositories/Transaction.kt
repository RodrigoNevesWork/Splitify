package project.Splitify.repositories

interface Transaction {

    val userRepository: UserRepository

    val tripRepository: TripRepository

    val purchaseRepository : PurchaseRepository

    fun rollback() // for testing methods
}