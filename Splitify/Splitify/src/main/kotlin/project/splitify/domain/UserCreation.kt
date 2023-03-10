package project.splitify.domain

import javax.validation.constraints.Email

data class UserCreation(
    val name : String,
    @field:Email(message = "Email must be a valid email address.")
    val email : String,
    val phone : String, val password : String
    )


