package project.splitify.http.PurchaseControler

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDate

data class Purchase(
    val id : String,
    val price : Float,
    @JsonInclude(JsonInclude.Include.NON_NULL) val description : String? = null,
    val trip_id : Int,
    val user_id : Int,
    val purchase_date : LocalDate
    )