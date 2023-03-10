package project.splitify.services

import org.springframework.stereotype.Component
import project.splitify.domain.*
import project.splitify.http.jwt.JwtUtils
import project.splitify.repositories.TransactionManager


@Component
class UserServices(
    private val transactionManager: TransactionManager,
    private val utils: Utils,
    private val jwtUtils: JwtUtils
) {

    private val PHONE_SIZE = 9

    private fun emailExists(email: String) =
        transactionManager.run{
            val encrypted = utils.encrypt(email)
            it.userRepository.checkIfEmailExists(encrypted)
        }

    private fun phoneExists(phone : String) : Boolean =
        transactionManager.run {
            val encrypted = utils.encrypt(phone)
            it.userRepository.checkIfPhoneExists(encrypted)
        }

    private fun checkDetails(email: String, phone: String, password: String){
        if(!email.contains("@")) throw BadEmail()
        if(phone.length != PHONE_SIZE) throw BadEmail()
        if(!utils.isPasswordSafe(password)) throw WeakPassword()
        if(emailExists(email)) throw EmailAlreadyInUse()
        if(phoneExists(phone)) throw PhoneAlreadyInUse()
    }

    fun getUserByToken(token : Token) : User?{
        return transactionManager.run {
            val hashedToken = utils.encrypt(token.token)
            it.userRepository.getUserByToken(hashedToken)
        }
    }

    fun getUserByEmail(email : String) : UserOutput{
        return transactionManager.run {
            val encryptedEmail = utils.encrypt(email)
            val userEncrypted = it.userRepository.getUserByEmail(encryptedEmail) ?: throw UserNotExists()
            utils.decryptUserOutput(userEncrypted)
        }
    }

    fun createUser(userCreation : UserCreation) : Pair<Int, String> {

        checkDetails(userCreation.email, userCreation.phone, userCreation.password)

        val jwtPayload = JwtUtils.JwtPayload.createJwtPayload(userCreation.phone)

        val token = jwtUtils.createAccessToken(jwtPayload)

        val userEncrypted = utils.encryptUserCreation(userCreation)

        val encryptedToken = utils.encrypt(token)

        val id = transactionManager.run { it.userRepository.createUser(encryptedToken, userEncrypted) }

        return Pair(id,token)
    }

    fun getUser(userId : Int) : UserOutput? {
        return transactionManager.run {
            val userEncripted = it.userRepository.getUser(userId) ?: throw UserNotExists()
             utils.decryptUserOutput(userEncripted)
        }
    }

}





