package project.splitify.http.tripController

import org.springframework.web.bind.annotation.*
import project.splitify.domain.*
import project.splitify.http.userController.User
import project.splitify.http.userController.UserInput
import project.splitify.http.media.Actions
import project.splitify.http.media.Links
import project.splitify.http.media.Problem.Companion.PROBLEM_MEDIA_TYPE
import project.splitify.http.media.Rels
import project.splitify.http.media.Uris
import project.splitify.http.media.siren.SirenModel
import project.splitify.http.pipeline.authentication.Authentication
import project.splitify.services.TripServices
import project.splitify.http.media.siren.SirenModel.Companion.SIREN_MEDIA_TYPE
import project.splitify.http.media.siren.SubEntity

@Authentication
@RestController
@RequestMapping(produces = [SIREN_MEDIA_TYPE, PROBLEM_MEDIA_TYPE])
class TripController(
    private val tripServices: TripServices
) {

    @PostMapping(Uris.Trips.CREATE)
    fun createTrip(@RequestBody tripCreation: TripCreation, user : User) : SirenModel<Int> {
        val id = tripServices.createTrip(user.id,tripCreation)
        return SirenModel(
            clazz = listOf(Rels.CREATE_TRIP),
            properties = id,
            links = listOf(Links.home, Links.userHome,Links.self(Uris.tripByID(id))),
            actions = listOf(
                Actions.addUserToTrip(id),
                Actions.createPurchase(id),
                Actions.tripInformation(id)
            )
        )
    }

    @PostMapping(Uris.Trips.USERS_IN_TRIP)
    fun addUserToTrip(@PathVariable trip_id : Int, user : User, @RequestBody userAdded : UserInput) : SirenModel<String>{
        tripServices.addUserToTrip(userAdded.id,trip_id,user.id)
        return SirenModel(
            properties = "User Added to Trip"
        )
    }

    @GetMapping(Uris.Trips.TRIP_BY_ID)
    fun getTripInformation(@PathVariable trip_id : Int, user: User) : SirenModel<Trip> {
        val tripInformation = tripServices.getTrip(trip_id, user.id)
        return SirenModel(
            clazz = listOf(Rels.TRIP_INFORMATION),
            properties = tripInformation.trip,
            links = listOf(
                Links.home,
                Links.userHome,
                Links.self(Uris.tripByID(trip_id))
            ),
            entities = tripInformation.purchases.map { purchase ->
                SubEntity.EmbeddedSubEntity(
                    rel = listOf(Rels.ITEM,Rels.PURCHASE),
                    properties = purchase,
                    links = listOf(
                        Links.self(Uris.purchase(purchase.trip_id,purchase.id)),
                        Links.home,
                        Links.userHome
                    ),
                    actions = listOf(
                        Actions.payPurchase(purchase.trip_id,purchase.id),
                        Actions.purchaseByID(purchase.trip_id,purchase.id)
                    )
                )
            }

        )
    }

}