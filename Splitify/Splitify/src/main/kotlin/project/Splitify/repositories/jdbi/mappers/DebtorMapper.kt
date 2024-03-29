package project.splitify.repositories.jdbi.mappers

import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import project.splitify.http.userController.userDTOS.Debtor
import project.splitify.http.userController.userDTOS.UserOutput
import java.sql.ResultSet

class DebtorMapper : RowMapper<Debtor> {
    override fun map(rs: ResultSet, ctx: StatementContext): Debtor {
        val user = UserOutput(
            id = rs.getInt("id"),
            name = rs.getString("name"),
            email = rs.getString("email"),
            phone = rs.getString("phone")
        )

        val debt = rs.getFloat("debt")
        val purchaseId = rs.getString("purchase_id")
        val description = rs.getString("description")
        return Debtor(purchaseId,description,user,debt)
    }
}

