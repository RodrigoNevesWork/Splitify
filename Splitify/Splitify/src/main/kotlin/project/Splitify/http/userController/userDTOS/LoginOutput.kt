package project.splitify.http.userController.userDTOS

import project.splitify.domain.JWToken

data class LoginOutput(val id : Int, val token : JWToken)