package project.splitify.repositories.jdbi

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import project.splitify.http.PurchaseControler.Purchase
import project.splitify.http.tripController.Trip
import project.splitify.http.tripController.Trips
import project.splitify.http.userController.*
import project.splitify.repositories.UserRepository
import project.splitify.repositories.jdbi.mappers.DebtMapper
import project.splitify.repositories.jdbi.mappers.DebtorMapper

class JbdiUserRepository(
    private val handle : Handle
) : UserRepository{
    override fun getUserByToken(token: String): User? {
        return handle
                .createQuery("select * from dbo.user where token = :token")
                .bind("token", token)
                .mapTo<User>()
                .singleOrNull()
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

    override fun createUser(token : String, userCreation: UserCreation): Int {

       return handle
                .createQuery("insert into dbo.user(password, name, email, phone, token) values (:password, :name, :email,:phone, :token) returning id")
                .bind("password", userCreation.password)
                .bind("name", userCreation.name)
                .bind("email", userCreation.email)
                .bind("phone", userCreation.phone)
                .bind("token", token)
                .mapTo<Int>()
                .single()

    }

    override fun getUser(userID: Int): UserOutput? {
       return handle
              .createQuery("select id,name,email,phone from dbo.user where id = :userID")
              .bind("userID",userID)
              .mapTo<UserOutput>()
              .singleOrNull()
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

    override fun isInTrip(userID: Int, tripID: Int): Boolean {
        return handle
            .createQuery("select count(*) from dbo.user_trip where user_id = :userID and trip_id = :tripID")
            .bind("userID", userID)
            .bind("tripID", tripID)
            .mapTo<Int>()
            .single() == 1
    }

    override fun getUsers(name: String): ListOfUsers {
        return ListOfUsers(
            handle
               .createQuery("select id,name,email,phone from dbo.user where name = :name ")
               .bind("name", name)
               .mapTo<UserOutput>()
               .toList()
        )
    }

    override fun getTripsOfUser(userID: Int): Trips {
        return Trips(
            handle
                .createQuery("select t.id, t.location from dbo.trip t inner join dbo.user_trip ut on t.id = ut.trip_id where ut.user_id = :userID ")
                .bind("userID",userID)
                .mapTo<Trip>()
                .toList()
        )
    }


    override fun getDebtsOfUserInTrip(userID: Int, tripID : Int): List<Debt> {
        val query = "WITH purchase_total_people AS (\n" +
                "    SELECT p.id AS purchase_id, p.price, p.trip_id, COUNT(ut.user_id) AS total_people\n" +
                "    FROM dbo.Purchase p\n" +
                "    JOIN dbo.User_Trip ut ON p.trip_id = ut.trip_id\n" +
                "    WHERE p.trip_id = :tripID\n" +
                "    GROUP BY p.id, p.price, p.trip_id\n" +
                "),\n" +
                "debts AS (\n" +
                "    SELECT p.id AS purchase_id, p.user_id AS buyer_id, p.price / pt.total_people AS debt_amount, p.description, p.trip_id\n" +
                "    FROM dbo.Purchase p\n" +
                "    JOIN purchase_total_people pt ON p.id = pt.purchase_id\n" +
                ")\n" +
                "SELECT p.id, p.price, p.description, p.trip_id, p.user_id, d.debt_amount AS debt\n" +
                "FROM debts d\n" +
                "JOIN dbo.Purchase p ON d.purchase_id = p.id\n" +
                "WHERE d.buyer_id != :userID AND NOT EXISTS (\n" +
                "    SELECT :userID\n" +
                "    FROM dbo.User_Purchase_Payed upp\n" +
                "    WHERE upp.user_id = :userID AND upp.purchase_id = d.purchase_id\n" +
                ");\n"

        return handle.createQuery(query)
                     .bind("tripID",tripID)
                     .bind("userID",userID)
                     .map(DebtMapper())
                     .toList()
    }

    override fun getDebtorsInTrip(userID: Int, tripID: Int): List<Debtor> {
        val query =
            "WITH user_purchase AS (\n" +
                "    SELECT p.id, p.price, p.description, p.trip_id, p.user_id\n" +
                "    FROM dbo.Purchase p\n" +
                "    WHERE p.user_id = :userID AND p.trip_id = :tripID\n" +
                "),\n" +
                "total_users_in_trip AS (\n" +
                "    SELECT COUNT(*) AS total_users\n" +
                "    FROM dbo.User_Trip ut\n" +
                "    WHERE ut.trip_id = :tripID\n" +
                "),\n" +
                "debts AS (\n" +
                "    SELECT up.id AS purchase_id, up.price, up.description, up.trip_id, up.user_id,\n" +
                "        ut.user_id AS debtor_id, (up.price / tt.total_users) AS debt\n" +
                "    FROM user_purchase up\n" +
                "    JOIN dbo.User_Trip ut ON ut.trip_id = up.trip_id\n" +
                "    CROSS JOIN total_users_in_trip tt\n" +
                "    WHERE up.user_id != ut.user_id\n" +
                "),\n" +
                "debts_with_paid_status AS (\n" +
                "    SELECT d.*, upp.user_id AS paid_id\n" +
                "    FROM debts d\n" +
                "    LEFT JOIN dbo.user_purchase_payed upp ON d.debtor_id = upp.user_id AND d.purchase_id = upp.purchase_id\n" +
                "    WHERE upp.purchase_id IS NULL OR upp.purchase_id = d.purchase_id\n" +
                ")\n" +
                "\n" +
                "SELECT u.id, u.name, u.email, u.phone, d.debt, d.purchase_id, d.description\n" +
                "FROM dbo.User u\n" +
                "JOIN debts_with_paid_status d ON u.id = d.debtor_id\n" +
                "WHERE d.paid_id IS NULL;\n"




        return handle.createQuery(query)
                     .bind("userID",userID)
                     .bind("tripID",tripID)
                     .map(DebtorMapper())
                     .toList()
    }

    override fun login(loginModel: LoginModel): LoginOutput? {
        return handle.createQuery("select token from dbo.user where email = :email and password = :password")
              .bind("email", loginModel.email)
              .bind("password", loginModel.password)
              .mapTo<LoginOutput>()
              .singleOrNull()
    }

}