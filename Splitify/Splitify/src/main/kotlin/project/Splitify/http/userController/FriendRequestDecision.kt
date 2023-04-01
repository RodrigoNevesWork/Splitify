package project.splitify.http.userController

data class FriendRequestDecision(val decision : Decision)

enum class Decision{
    ACCEPT,
    DECLINE
}