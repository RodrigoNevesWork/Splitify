package project.Splitify.http

import org.springframework.web.bind.annotation.RestController
import project.Splitify.services.PurchaseServices

@RestController
class PurchasesController(
    private val purchaseServices: PurchaseServices
) {

}