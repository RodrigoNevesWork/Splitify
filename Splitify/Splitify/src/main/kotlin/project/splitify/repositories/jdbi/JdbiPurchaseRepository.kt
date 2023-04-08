package project.splitify.repositories.jdbi

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import project.splitify.http.purchaseControler.purchaseDTO.Purchase
import project.splitify.repositories.PurchaseRepository

class JdbiPurchaseRepository(
    private val handle : Handle
) : PurchaseRepository {

        override fun addPurchase(purchase: Purchase) {
            handle
                .createUpdate("insert into dbo.purchase values(:id, :price, :description, :purchase_date,:user_id, :trip_id)")
                .bind("id", purchase.id)
                .bind("price", purchase.price)
                .bind("description", purchase.description)
                .bind("purchase_date", purchase.purchase_date)
                .bind("user_id", purchase.user_id)
                .bind("trip_id", purchase.trip_id)
                .execute()

            handle
                .createUpdate("insert into dbo.user_purchase_payed values (:userID,:purchaseID)")
                .bind("userID", purchase.user_id )
                .bind("purchaseID", purchase.id )
                .execute()
        }

    override fun checkBuyer(purchaseID: String, userID: Int) : Boolean {
       return handle
            .createQuery("select count(*) from dbo.purchase where id = :purchaseID and user_id = :userID")
            .bind("purchaseID",purchaseID)
            .bind("userID",userID)
            .mapTo<Int>()
            .single() == 1

    }

    override fun checkIfHasAlreadyPayed(purchaseID: String, userID: Int) : Boolean{
        return handle
            .createQuery("select count(*) from dbo.user_purchase_payed where purchase_id = :purchaseID and user_id = :userID")
            .bind("purchaseID",purchaseID)
            .bind("userID",userID)
            .mapTo<Int>()
            .single() == 1
    }

    override fun payPurchase(purchaseID: String, payingUser: Int) {
         handle
            .createUpdate("insert into dbo.user_purchase_payed values(:userID,:purchaseID)")
            .bind("userID", payingUser)
            .bind("purchaseID", purchaseID)
            .execute()

    }

    override fun getPurchaseInformation(purchaseID: String) : Purchase? {
        return handle
            .createQuery("select * from dbo.purchase where id = :purchaseID")
            .bind("purchaseID",purchaseID)
            .mapTo<Purchase>()
            .singleOrNull()
    }
}