package project.splitify.domain

data class FriendRequestDecision(val decision : Decision)

enum class Decision{
    ACCEPT,
    DECLINE
}