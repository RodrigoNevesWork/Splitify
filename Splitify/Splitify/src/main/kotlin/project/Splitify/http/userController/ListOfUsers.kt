package project.splitify.http.userController

data class ListOfUsers(val users : List<UserOutput>){

    fun  map(transform: (UserOutput) -> UserOutput): ListOfUsers = ListOfUsers( users.map { transform(it) } )


}
