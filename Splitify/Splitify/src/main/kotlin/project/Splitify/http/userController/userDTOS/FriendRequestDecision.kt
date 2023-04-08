package project.splitify.http.userController.userDTOS

data class FriendRequestDecision(val decision : Decision)

enum class Decision{
    ACCEPT,
    DECLINE
}