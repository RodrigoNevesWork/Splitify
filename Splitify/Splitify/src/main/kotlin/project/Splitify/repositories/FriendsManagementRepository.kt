package project.splitify.repositories

import project.splitify.domain.Friend
import project.splitify.domain.FriendRequest
import java.util.*

interface FriendsManagementRepository {
    fun createFriendRequest( id : String ,userID : Int, userRequesting : Int)
    fun declineFriendRequest(id : String)
    fun acceptFriendRequest(id : String)
    fun getFriendsRequests(id : Int) : List<FriendRequest>
    fun getFriendsList(id : Int) : List<Friend>
    fun areFriends(user1 : Int, user2 : Int) : Boolean
}