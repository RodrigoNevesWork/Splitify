package project.Splitify.repositories

import project.Splitify.domain.Purchase
import project.Splitify.domain.Token
import project.Splitify.domain.User
import project.Splitify.domain.UserCreation

interface UserRepository {

    fun getUserByToken(token : String) : User
    fun checkIfEmailExists(email : String) : Boolean
    fun checkIfPhoneExists(phone : String) : Boolean
    fun createUser(token : Token, userCreation : UserCreation) : Int
    fun readUser(userID : Int) : User
    fun updateUser(userID: Int, userCreation : UserCreation)
    fun deleteUser(userID: Int)
    fun getPurchasesFromTrip(userID : Int, tripID : Int) : List<Purchase>
    fun checkIfIsInTrip(userID : Int, tripID: Int) : Boolean

}