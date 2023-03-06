package project.Splitify

import org.jdbi.v3.core.Jdbi
import org.postgresql.ds.PGSimpleDataSource
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import project.Splitify.repositories.jdbi.configure

@SpringBootApplication
class SplitifyApplication {
	@Bean
	fun jdbi(): Jdbi = Jdbi.create(
		PGSimpleDataSource().apply {
			setURL(System.getenv("DATABASE_DAW"))
		}

	).configure()
}


fun main(args: Array<String>) {
	runApplication<SplitifyApplication>(*args)
}
