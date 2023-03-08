package project.Splitify.repositories

import project.Splitify.domain.*

interface UserRepository {

    fun getUserByToken(token : String) : User?
    fun checkIfEmailExists(email : String) : Boolean
    fun checkIfPhoneExists(phone : String) : Boolean
    fun createUser(token : Token, userCreation : UserCreation) : Int
    fun getUser(userID : Int) : UserOutput?
    fun updateUser(userID: Int, userCreation : UserCreation)
    fun deleteUser(userID: Int)
    fun getPurchasesFromTrip(userID : Int, tripID : Int) : List<Purchase>
    fun checkIfIsInTrip(userID : Int, tripID: Int) : Boolean
    fun getUserByEmail(email: String) : UserOutput?

}