package project.splitify.domain

data class Purchase(val id : String, val price : Float, val description : String? = null, val trip_id : Int, val user_id : Int)