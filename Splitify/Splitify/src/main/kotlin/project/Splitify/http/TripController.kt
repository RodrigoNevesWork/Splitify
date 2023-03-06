package project.Splitify.http

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import project.Splitify.domain.TripCreation
import project.Splitify.domain.User
import project.Splitify.services.TripServices

@RestController
class TripController(
    private val tripServices: TripServices
) {

    @PostMapping(Uris.Trip.CREATE)
    fun createTrip(@RequestBody tripCreation: TripCreation, user : User) : ResponseEntity<Int> {
        val id = tripServices.createTrip(user.id,tripCreation)
        return ResponseEntity.status(200).body(id)
    }

}