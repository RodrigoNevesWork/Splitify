package project.Splitify.repositories.jdbi

import org.jdbi.v3.core.Handle
import project.Splitify.domain.Purchase
import project.Splitify.repositories.PurchaseRepository

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
    }
}