package project.splitify.http.jwt

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
class JwtConfiguration(
    @Value("\${server.config.secrets.access-token-secret}")
    val accessTokenSecret: String
    )