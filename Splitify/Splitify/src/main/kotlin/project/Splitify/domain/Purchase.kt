package project.Splitify.domain

import java.util.*

data class Purchase(val id : UUID, val price : Float, val description : String? = null, val trip_id : Int, val user_id : Int)