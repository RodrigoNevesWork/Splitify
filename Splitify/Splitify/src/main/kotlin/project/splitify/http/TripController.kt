package project.splitify.http

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import project.splitify.domain.*
import project.splitify.http.media.Problem.Companion.PROBLEM_MEDIA_TYPE
import project.splitify.http.media.Uris
import project.splitify.http.pipeline.authentication.Authentication
import project.splitify.services.TripServices
import project.splitify.http.media.siren.SirenModel.Companion.SIREN_MEDIA_TYPE

@Authentication
@RestController
@RequestMapping(produces = [SIREN_MEDIA_TYPE, PROBLEM_MEDIA_TYPE])
class TripController(
    private val tripServices: TripServices
) {

    @PostMapping(Uris.Trips.CREATE)
    fun createTrip(@RequestBody tripCreation: TripCreation, user : User) : ResponseEntity<String> {
        val id = tripServices.createTrip(user.id,tripCreation)
        return ResponseEntity.status(201).body("Trip added with the id $id")
    }

    @PostMapping(Uris.Trips.USERS_IN_TRIP)
    fun addUserToTrip(@PathVariable trip_id : Int , user : User, @RequestBody userAdded : UserInput) : ResponseEntity<String>{
        tripServices.addUserToTrip(userAdded.id,trip_id,user.id)
        return ResponseEntity.ok("User Added to Trip")
    }

    @GetMapping(Uris.Trips.TRIP_BY_ID)
    fun getTripInformation(@PathVariable trip_id : Int, user: User) : ResponseEntity<TripPurchases> {
        val tripInformation = tripServices.getTrip(trip_id, user.id)
        return ResponseEntity.ok(tripInformation)
    }

}