package project.splitify.http

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.splitify.domain.Purchase
import project.splitify.domain.PurchaseCreation
import project.splitify.domain.User
import project.splitify.domain.UserInput
import project.splitify.http.media.Problem.Companion.PROBLEM_MEDIA_TYPE
import project.splitify.http.media.Uris
import project.splitify.http.media.siren.SirenModel.Companion.SIREN_MEDIA_TYPE
import project.splitify.http.pipeline.authentication.Authentication
import project.splitify.services.PurchaseServices

@Authentication
@RestController
@RequestMapping(produces = [SIREN_MEDIA_TYPE,PROBLEM_MEDIA_TYPE])
class PurchasesController(
    private val purchaseServices: PurchaseServices
) {

    @PostMapping(Uris.Purchases.CREATE)
    fun createPurchase(@PathVariable trip_id : Int, user : User, @RequestBody purchaseCreation : PurchaseCreation) : ResponseEntity<String>{
        val id = purchaseServices.createPurchase(purchaseCreation, user.id,trip_id)
        return ResponseEntity.ok("Purchase Created with id -> $id")
    }

    @PostMapping(Uris.Purchases.PURCHASE)
    fun payPurchase(@PathVariable purchase_id : String, user: User, @RequestBody payingUser : UserInput, @PathVariable trip_id : Int, ) : ResponseEntity<String>{
        purchaseServices.payPurchase(purchase_id,user.id, payingUser.id, trip_id)
        return ResponseEntity.ok("Purchase Payed")
    }

    @GetMapping(Uris.Purchases.PURCHASE)
    fun purchaseInformation(@PathVariable purchase_id: String, user: User, @PathVariable trip_id: Int) : ResponseEntity<Purchase> {
        val purchase = purchaseServices.getPurchaseInformation(purchase_id,user.id,trip_id)
        return ResponseEntity.ok(purchase)

    }
}
