package project.splitify.http.media

import org.springframework.http.ResponseEntity
import java.net.URI

data class Problem(
    val type: URI,
    val title: String,
    val status: Int,
    val detail: String? = null,
    val instance: URI? = null
) {

    fun toResponseEntity() = ResponseEntity
        .status(status)
        .header("Content-Type", PROBLEM_MEDIA_TYPE)
        .body<Any>(this)

    companion object {
        private const val APPLICATION_TYPE = "application"
        private const val PROBLEM_SUBTYPE = "problem+json"
        const val PROBLEM_MEDIA_TYPE = "$APPLICATION_TYPE/$PROBLEM_SUBTYPE"

    }
}