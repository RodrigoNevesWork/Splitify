package project.Splitify.http

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import project.Splitify.domain.PurchaseCreation
import project.Splitify.domain.User
import project.Splitify.services.PurchaseServices

@RestController
class PurchasesController(
    private val purchaseServices: PurchaseServices
) {
    @PostMapping(Uris.Purchase.CREATE)
    fun createPurchase(@PathVariable trip_id : Int, user : User, @RequestBody purchaseCreation : PurchaseCreation) : ResponseEntity<String>{
        val id = purchaseServices.createPurchase(purchaseCreation, user.id,trip_id)
        return ResponseEntity.ok().body("Purchase Created with id -> $id")
    }
}
