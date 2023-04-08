package project.splitify.repositories.jdbi.mappers

import org.jdbi.v3.core.mapper.RowMapper
import org.jdbi.v3.core.statement.StatementContext
import project.splitify.http.userController.userDTOS.Debt
import project.splitify.http.purchaseControler.purchaseDTO.Purchase
import java.sql.ResultSet

class DebtMapper : RowMapper<Debt> {
    override fun map(rs: ResultSet, ctx: StatementContext): Debt {
        val purchase = Purchase(
            id = rs.getString("id"),
            price = rs.getFloat("price"),
            description = rs.getString("description"),
            trip_id = rs.getInt("trip_id"),
            user_id = rs.getInt("user_id"),
            purchase_date = rs.getDate("purchase_date").toLocalDate()
        )

        val debt = rs.getFloat("debt")

        return Debt(purchase, debt)
    }
}
