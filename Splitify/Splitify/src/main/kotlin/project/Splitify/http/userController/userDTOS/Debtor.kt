package project.splitify.http.userController.userDTOS

data class Debtor(val purchaseID : String, val description : String, val user : UserOutput, val debt : Float)