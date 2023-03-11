package project.splitify.http

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import project.splitify.domain.PurchaseCreation
import project.splitify.domain.User
import project.splitify.domain.UserInput
import project.splitify.http.pipeline.Authentication
import project.splitify.services.PurchaseServices
import java.util.UUID

@Authentication
@RestController
class PurchasesController(
    private val purchaseServices: PurchaseServices
) {

    @PostMapping(Uris.Purchase.CREATE)
    fun createPurchase(@PathVariable trip_id : Int, user : User, @RequestBody purchaseCreation : PurchaseCreation) : ResponseEntity<String>{
        val id = purchaseServices.createPurchase(purchaseCreation, user.id,trip_id)
        return ResponseEntity.ok("Purchase Created with id -> $id")
    }

    @PostMapping(Uris.Purchase.PURCHASE)
    fun payPurchase(@PathVariable purchase_id : UUID, user: User, @RequestBody payingUser : UserInput) : ResponseEntity<String>{
        purchaseServices.payPurchase(purchase_id,user.id, payingUser.id)
        return ResponseEntity.ok("Purchase Payed")
    }
}
