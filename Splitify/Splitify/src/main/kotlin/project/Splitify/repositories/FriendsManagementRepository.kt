package project.splitify.repositories

import project.splitify.http.userController.Friend
import project.splitify.http.userController.FriendRequest

interface FriendsManagementRepository {
    fun createFriendRequest( id : String ,userID : Int, userRequesting : Int)
    fun declineFriendRequest(id : String)
    fun acceptFriendRequest(id : String)
    fun getFriendsRequests(id : Int) : List<FriendRequest>
    fun getFriendsList(id : Int) : List<Friend>
    fun areFriends(user1 : Int, user2 : Int) : Boolean
}