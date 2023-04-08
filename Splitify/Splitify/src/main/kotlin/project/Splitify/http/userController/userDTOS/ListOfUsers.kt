package project.splitify.http.userController.userDTOS

data class ListOfUsers(val users : List<UserOutput>){

    fun  map(transform: (UserOutput) -> UserOutput): ListOfUsers = ListOfUsers( users.map { transform(it) } )


}
