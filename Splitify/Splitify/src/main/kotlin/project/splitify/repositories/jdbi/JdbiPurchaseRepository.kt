package project.splitify.repositories.jdbi

import org.jdbi.v3.core.Handle
import project.splitify.domain.Purchase
import project.splitify.repositories.PurchaseRepository

class JdbiPurchaseRepository(
    private val handle : Handle
) : PurchaseRepository {

    override fun addPurchase(purchase: Purchase) {
        handle
            .createUpdate("insert into dbo.purchase values(:id, :price, :description, :user_id, :trip_id)")
            .bind("id", purchase.id)
            .bind("price", purchase.price)
            .bind("description", purchase.description)
            .bind("user_id", purchase.user_id)
            .bind("trip_id", purchase.trip_id)
            .execute()

        handle
            .createUpdate("insert into dbo.user_purchase_payed values (:userID,:purchaseID)")
            .bind("userID", purchase.user_id )
            .bind("purchaseID", purchase.id )
            .execute()
    }
}