package project.Splitify.repositories.jdbi

import project.Splitify.repositories.TransactionManager
import org.jdbi.v3.core.Jdbi
import org.springframework.stereotype.Component
import project.Splitify.domain.DomainException
import project.Splitify.repositories.Transaction

@Component
class JdbiTransactionManager(
    private val jdbi: Jdbi
) : TransactionManager {

    override fun <R> run(block: (Transaction) -> R): R =
        jdbi.inTransaction<R, DomainException> { handle ->
            val transaction = JdbiTransaction(handle)
            block(transaction)
        }
}