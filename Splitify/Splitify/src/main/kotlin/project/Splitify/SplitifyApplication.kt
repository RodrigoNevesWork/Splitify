package project.splitify

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.postgres.PostgresPlugin
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

fun Jdbi.configure(): Jdbi {
	installPlugin(KotlinPlugin())
	installPlugin(PostgresPlugin())
	return this
}

@SpringBootApplication
class SplitifyApplication {

	@Bean
	fun jdbi(): Jdbi = Jdbi.create(
		PGSimpleDataSource().apply {
			setURL(System.getenv("postgresql_database"))
		}
	).configure()
}


fun main(args: Array<String>) {
	runApplication<SplitifyApplication>(*args)
}
