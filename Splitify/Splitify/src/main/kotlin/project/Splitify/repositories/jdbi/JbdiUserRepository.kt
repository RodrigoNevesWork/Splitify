package project.Splitify.repositories.jdbi

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import project.Splitify.domain.Purchase
import project.Splitify.domain.Token
import project.Splitify.domain.User
import project.Splitify.domain.UserCreation
import project.Splitify.repositories.UserRepository
import java.util.*

class JbdiUserRepository(
    private val handle : Handle
) : UserRepository{
    override fun getUserByToken(token: String): User {
        return handle
                .createQuery("select * from dbo.user where token = :token")
                .bind("token", token)
                .mapTo<User>()
                .single()
    }

    override fun checkIfEmailExists(email: String): Boolean {
       return handle
            .createQuery("select count(*) from dbo.user where email = :email")
            .bind("email", email)
            .mapTo<Int>()
            .single() == 1

    }

    override fun checkIfPhoneExists(phone: String): Boolean {
        return handle
            .createQuery("select count(*) from dbo.user where phone = :phone")
            .bind("phone", phone)
            .mapTo<Int>()
            .single() == 1
    }

    override fun createUser(token : Token, userCreation: UserCreation): Int {

       return handle
                .createQuery("insert into dbo.user(password, name, email, phone, token) values (:password, :name, :email,:phone, :token) returning id")
                .bind("password", userCreation.password)
                .bind("name", userCreation.name)
                .bind("email", userCreation.email)
                .bind("phone", userCreation.phone)
                .bind("token", token.token)
                .mapTo<Int>()
                .single()

    }

    override fun readUser(userID: Int): User {
       return handle
              .createQuery("select * from dbo.user where id = :userID")
              .bind("userID",userID)
              .mapTo<User>()
              .single()
    }

    override fun updateUser(userID: Int, userCreation: UserCreation) {
        handle
            .createUpdate("update dbo.user set name = :name, email = :email, phone = :phone where id = :id")
            .bind("name", userCreation.name)
            .bind("email", userCreation.email)
            .bind("phone", userCreation.phone)
            .bind("id", userID)
    }

    override fun deleteUser(userID: Int) {
        handle
            .createUpdate("delete from dbo.user where id = :id")
            .bind("id", userID)
    }

    override fun getPurchasesFromTrip(userID: Int, tripID: Int): List<Purchase> =
        handle
            .createQuery("select * from dbo.purchase where user_id = :userID and trip_id = :tripID ")
            .bind("userID", userID)
            .bind("tripID", tripID)
            .mapTo<Purchase>()
            .toList()

    override fun checkIfIsInTrip(userID: Int, tripID: Int): Boolean {
        return handle
            .createQuery("select count(*) from dbo.user_trip where user_id = :userID and trip_id = :tripID")
            .bind("userID", userID)
            .bind("tripID", tripID)
            .mapTo<Int>()
            .single() == 1
    }


}