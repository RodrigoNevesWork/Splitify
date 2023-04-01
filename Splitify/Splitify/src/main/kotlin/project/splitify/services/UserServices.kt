package project.splitify.services

import org.springframework.stereotype.Service
import project.splitify.domain.*
import project.splitify.http.userController.*
import project.splitify.http.jwt.JwtUtils
import project.splitify.http.tripController.Trips
import project.splitify.repositories.TransactionManager


@Service
class UserServices(
    private val transactionManager: TransactionManager,
    private val encryptionUtils: EncryptionUtils,
    private val jwtUtils: JwtUtils
) {

    private fun isPasswordSafe(password : String) : Boolean{
        val regex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}\$")
        return regex.matches(password)
    }

    private val PHONE_SIZE = 9 //Portuguese phone number size

    private fun emailExists(email: String) =
        transactionManager.run{
            val encrypted = encryptionUtils.encrypt(email)
            it.userRepository.checkIfEmailExists(encrypted)
        }

    private fun phoneExists(phone : String) : Boolean =
        transactionManager.run {
            val encrypted = encryptionUtils.encrypt(phone)
            it.userRepository.checkIfPhoneExists(encrypted)
        }

    private fun checkDetails(email: String, phone: String, password: String){
        if(!email.contains("@")) throw BadEmail()
        if(phone.length != PHONE_SIZE) throw BadPhone()
        if(!isPasswordSafe(password)) throw WeakPassword()
        if(emailExists(email)) throw EmailAlreadyInUse()
        if(phoneExists(phone)) throw PhoneAlreadyInUse()
    }

    fun getUserByToken(JWToken : JWToken) : User?{
        return transactionManager.run {
            val encryptedToken = encryptionUtils.encrypt(JWToken.token)
            it.userRepository.getUserByToken(encryptedToken)
        }
    }

    fun login(loginModel: LoginModel) : LoginOutput {
        val encryptedPassword = encryptionUtils.encrypt(loginModel.password)

        val credentials = transactionManager.run {
            it.userRepository.login(LoginModel(loginModel.email,encryptedPassword))
        } ?: throw Unauthorized()

        return credentials
    }

    fun getUserByName(name : String) : ListOfUsers {
        return transactionManager.run {
            val encryptedName = encryptionUtils.encrypt(name)
            val usersEncrypted = it.userRepository.getUsers(encryptedName)
            usersEncrypted.map { userOutput ->  encryptionUtils.decryptUserOutput(userOutput) }
        }
    }

    fun createUser(userCreation : UserCreation) : Pair<Int, JWToken> {

        checkDetails(userCreation.email, userCreation.phone, userCreation.password)

        val jwtPayload = JwtUtils.JwtPayload.createJwtPayload(userCreation)

        val accesToken = jwtUtils.createJWToken(jwtPayload)

        val userEncrypted = encryptionUtils.encryptUserCreation(userCreation)

        val encryptedToken = encryptionUtils.encrypt(accesToken.token)

        val id = transactionManager.run { it.userRepository.createUser(encryptedToken, userEncrypted) }

        return Pair(id,accesToken)
    }


    fun getTripsOfUser(userID: Int) : Trips {
        return transactionManager.run {
            it.userRepository.getTripsOfUser(userID)
        }
    }

    fun deleteUser(userID : Int, userFromCookie: Int){

        if(userFromCookie != userID) throw Unauthorized()

        transactionManager.run {
            it.userRepository.getUser(userID) ?: throw UserNotExists()
            it.userRepository.deleteUser(userID)
        }
    }

    fun getDebtsOfUserInTrip(userID : Int, tripID : Int) : List<Debt>{
        return transactionManager.run {
            if(!it.userRepository.isInTrip(userID, tripID)) throw NotInThisTrip()
            it.userRepository.getDebtsOfUserInTrip(userID, tripID)
        }
    }

    fun getDebtorsInTrip(userID : Int, tripID : Int) : List<Debtor>{
          return transactionManager.run {
            if(!it.userRepository.isInTrip(userID, tripID)) throw NotInThisTrip()
            val debtorsEncrypted = it.userRepository.getDebtorsInTrip(userID, tripID)
            debtorsEncrypted.map { debtor ->
                    Debtor( debtor.purchaseID,debtor.description,encryptionUtils.decryptUserOutput(debtor.user), debtor.debt)
             }
        }
    }

}





