package project.splitify.http

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import project.splitify.domain.TripCreation
import project.splitify.domain.TripInput
import project.splitify.domain.User
import project.splitify.http.pipeline.Authentication
import project.splitify.services.TripServices

@Authentication
@RestController
class TripController(
    private val tripServices: TripServices
) {

    @PostMapping(Uris.Trip.CREATE)
    fun createTrip(@RequestBody tripCreation: TripCreation, user : User) : ResponseEntity<String> {
        val id = tripServices.createTrip(user.id,tripCreation)
        return ResponseEntity.status(200).body("Trip added with the id $id")
    }

    @PostMapping(Uris.User.BY_ID)
    fun addUserToTrip(@PathVariable user_id : Int, user : User, @RequestBody trip : TripInput) : ResponseEntity<String>{
        tripServices.addUserToTrip(user_id,trip.id,user.id)
        return ResponseEntity.status(200).body("User Added to Trip")
    }

}