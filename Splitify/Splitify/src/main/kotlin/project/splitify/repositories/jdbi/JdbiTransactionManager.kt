package project.splitify.repositories.jdbi

import project.splitify.repositories.TransactionManager
import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import project.splitify.repositories.Transaction

@Component
class JdbiTransactionManager(
    private val jdbi: Jdbi
) : TransactionManager {

    override fun <R> run(block: (Transaction) -> R): R =
        jdbi.inTransaction<R, Exception> { handle ->
            val transaction = JdbiTransaction(handle)
            block(transaction)
        }
}