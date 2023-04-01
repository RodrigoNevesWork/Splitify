package project.splitify.repositories

import project.splitify.http.PurchaseControler.Purchase
import project.splitify.http.tripController.Trips
import project.splitify.http.userController.*

interface UserRepository {

    fun getUserByToken(token : String) : User?
    fun checkIfEmailExists(email : String) : Boolean
    fun checkIfPhoneExists(phone : String) : Boolean
    fun createUser(token : String, userCreation : UserCreation) : Int
    fun getUser(userID : Int) : UserOutput?
    fun updateUser(userID: Int, userCreation : UserCreation)
    fun deleteUser(userID: Int)
    fun getPurchasesFromTrip(userID : Int, tripID : Int) : List<Purchase>
    fun isInTrip(userID : Int, tripID: Int) : Boolean
    fun getUsers(name: String) : ListOfUsers
    fun getTripsOfUser(userID : Int) : Trips
    fun getDebtsOfUserInTrip(userID : Int, tripID : Int) : List<Debt>
    fun getDebtorsInTrip(userID : Int, tripID : Int) : List<Debtor>
    fun login(loginModel: LoginModel): LoginOutput?

}