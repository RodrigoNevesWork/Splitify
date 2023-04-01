package project.splitify.http.PurchaseControler

import org.springframework.web.bind.annotation.*
import project.splitify.http.userController.User
import project.splitify.http.userController.UserInput
import project.splitify.http.media.Actions
import project.splitify.http.media.Links
import project.splitify.http.media.Problem.Companion.PROBLEM_MEDIA_TYPE
import project.splitify.http.media.Rels
import project.splitify.http.media.Uris
import project.splitify.http.media.siren.SirenModel
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
    fun createPurchase(@PathVariable trip_id : Int, user : User, @RequestBody purchaseCreation : PurchaseCreation) : SirenModel<String> {
        val id = purchaseServices.createPurchase(purchaseCreation, user.id,trip_id)
        return SirenModel(
            clazz = listOf(Rels.CREATE_PURCHASE),
            properties = id,
            links = listOf(
                Links.self(Uris.purchase(trip_id,id)),
                Links.home,
                Links.userHome
            ),
            actions = listOf(
                Actions.payPurchase(trip_id,id)
            )
        )
    }

    @PostMapping(Uris.Purchases.PURCHASE)
    fun payPurchase(@PathVariable purchase_id : String, user: User, @RequestBody payingUser : UserInput, @PathVariable trip_id : Int, ) : SirenModel<String>{
        purchaseServices.payPurchase(purchase_id,user.id, payingUser.id, trip_id)
        return SirenModel(
            properties = "Purchase payed"
        )
    }

    @GetMapping(Uris.Purchases.PURCHASE)
    fun purchaseInformation(@PathVariable purchase_id: String, user: User, @PathVariable trip_id: Int) : SirenModel<Purchase> {
        val purchase = purchaseServices.getPurchaseInformation(purchase_id,user.id,trip_id)
        return SirenModel(
            clazz = listOf(Rels.PURCHASE_INFORMATION),
            properties = purchase,
            links = listOf(
                Links.self(Uris.purchase(trip_id,purchase_id)),
                Links.home,
                Links.userHome
            ),
            actions = listOf(
                Actions.payPurchase(trip_id,purchase_id)
            )

        )

    }
}
