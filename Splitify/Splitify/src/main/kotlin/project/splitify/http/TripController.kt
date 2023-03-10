package project.splitify.http

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import project.splitify.domain.TripCreation
import project.splitify.domain.User
import project.splitify.services.TripServices

@RestController
class TripController(
    private val tripServices: TripServices
) {

    @PostMapping(Uris.Trip.CREATE)
    fun createTrip(@RequestBody tripCreation: TripCreation, user : User) : ResponseEntity<String> {
        val id = tripServices.createTrip(user.id,tripCreation)
        return ResponseEntity.status(200).body("Trip added with the id $id")
    }

}