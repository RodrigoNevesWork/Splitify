package project.splitify.services

import org.springframework.stereotype.Service
import project.splitify.domain.*
import project.splitify.http.userController.userDTOS.Decision
import project.splitify.http.userController.userDTOS.Friend
import project.splitify.http.userController.userDTOS.FriendRequest
import project.splitify.http.userController.userDTOS.FriendRequestDecision
import project.splitify.repositories.TransactionManager
import java.util.UUID

@Service
class FriendManagementServices(
    private val transactionManager: TransactionManager
) {
    fun makeFriendRequest(userID : Int, userRequesting : Int) : String{

        return transactionManager.run {

            if(it.friendsManagementRepository.areFriends(userID, userRequesting)) throw AlreadyFriends()

            val idOfRequest = UUID.randomUUID().toString()

            it.friendsManagementRepository.createFriendRequest(idOfRequest, userID, userRequesting)

            idOfRequest
        }
    }

    fun decisionAboutRequest(decision: FriendRequestDecision, id : String){
            transactionManager.run {

                if(decision.decision == Decision.DECLINE){
                    it.friendsManagementRepository.declineFriendRequest(id)
                } else{
                    it.friendsManagementRepository.acceptFriendRequest(id)
                }

            }
    }

    fun getFriendsRequest(userID : Int) : List<FriendRequest>{
        return transactionManager.run {
            it.friendsManagementRepository.getFriendsRequests(userID)
        }
    }

    fun getFriendsList(userID : Int, userRequesting: Int) : List<Friend>{
        return transactionManager.run {
            if(!it.friendsManagementRepository.areFriends(userID,userRequesting)) throw Unauthorized()
            it.friendsManagementRepository.getFriendsList(userID)
        }
    }

}