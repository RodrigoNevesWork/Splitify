package project.splitify.repositories.jdbi

import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import project.splitify.domain.Friend
import project.splitify.domain.FriendRequest
import project.splitify.repositories.FriendsManagementRepository
import java.util.*

class JdbiFriendsManagementRepository(
    private val handle : Handle
) : FriendsManagementRepository {
    override fun createFriendRequest(id: String, userID: Int, userRequesting: Int) {
        handle.createUpdate("insert into dbo.friend_request (id, user_id, user_requesting) values (:id,:userID,:userRequesting)")
              .bind("id", id)
              .bind("userID", userID)
              .bind("userRequesting", userRequesting)
              .execute()
    }

    override fun declineFriendRequest(id: String) {
        handle.createUpdate("delete from dbo.friend_request where id = :id")
              .bind("id", id)
              .execute()
    }

    override fun acceptFriendRequest(id: String) {
        val query = "WITH request_users AS (\n" +
                "  SELECT user_id, user_requesting\n" +
                "  FROM dbo.friend_request\n" +
                "  WHERE id = :id \n" +
                "), deleted_request AS (\n" +
                "  DELETE FROM dbo.friend_request\n" +
                "  WHERE id = :id \n" +
                "  RETURNING *\n" +
                ")\n" +
                "INSERT INTO dbo.friend_list (user_id_1, user_id_2)\n" +
                "SELECT user_id, user_requesting\n" +
                "FROM request_users;\n"

        handle.createUpdate(query)
              .bind("id",id)
              .execute()

    }

    override fun getFriendsRequests(id: Int): List<FriendRequest> {
        return handle.createQuery("select * from dbo.friend_request where user_id = :id")
              .bind("id",id)
              .mapTo<FriendRequest>()
              .toList()
    }

    override fun getFriendsList(id: Int): List<Friend> {
        return handle.createQuery("select * from dbo.friend_list where user_id_1 = :id or user_id_2 = :id")
            .bind("id",id)
            .mapTo<Friend>()
            .toList()
    }

    override fun areFriends(user1: Int, user2: Int): Boolean {
        return handle.createQuery("select count(*) from dbo.friend_list where (user_id_1 = :user1 and user_id_2 = :user2) or (user_id_1 = :user2 and user_id_2 = :user1) ")
                     .bind("user1", user1)
                     .bind("user2", user2)
                     .mapTo<Int>()
                     .single() == 1

    }
}