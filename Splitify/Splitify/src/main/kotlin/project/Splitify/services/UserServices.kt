package project.Splitify.services

import project.Splitify.repositories.TransactionManager
import org.springframework.stereotype.Component
import project.Splitify.domain.*
import java.security.MessageDigest
import java.util.*


@Component
class UserServices(
    private val transactionManager: TransactionManager
) {

    private fun hashPassword(password: String): String {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hash = messageDigest.digest(password.toByteArray(Charsets.UTF_8))
        return hash.joinToString("") {
            "%02x".format(it)
        }
    }

    private fun String.isSafe() : Boolean{
        val regex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}\$")
        return regex.matches(this)
    }

    private fun emailExists(email: String) =
        transactionManager.run{
            it.userRepository.checkIfEmailExists(email)
        }

    private fun phoneExists(phone : String) : Boolean =
        transactionManager.run {
            it.userRepository.checkIfPhoneExists(phone)
        }

    private fun checkDetails(email: String, phone: String, password: String){
        if(!password.isSafe()) throw WeakPassword()
        if(emailExists(email)) throw EmailAlreadyInUse()
        if(phoneExists(phone)) throw PhoneAlreadyInUse()
    }

    fun getUserByToken(token : String) : User{
        return transactionManager.run {
            it.userRepository.getUserByToken(token)
        }
    }


    fun createUser(userCreation : UserCreation) : Pair<Int, Token> {

        checkDetails(userCreation.email, userCreation.phone, userCreation.password)

        val token = Token(UUID.randomUUID().toString())

        val id = transactionManager.run {
                    it.userRepository.createUser(token, userCreation.copy(password = hashPassword(userCreation.password)))
                     }
        return Pair(id,token)
    }

    fun getUser(userId : Int) : User {
        return transactionManager.run {
            it.userRepository.readUser(userId)
        }
    }

    fun updateUser(userID : Int, userCreation: UserCreation){
        transactionManager.run {
            it.userRepository.updateUser(userID,userCreation)
        }
    }

    fun deleteUser(userID : Int) =
        transactionManager.run {
            it.userRepository.deleteUser(userID)
        }

    }





