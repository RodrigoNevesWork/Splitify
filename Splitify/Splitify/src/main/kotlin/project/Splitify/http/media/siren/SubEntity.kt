package project.splitify.http.media.siren


import org.springframework.http.MediaType
import java.net.URI

/**
 * A sub-entity is an entity that is part of another entity.
 * It is represented as a link with an embedded representation.
 */
sealed class SubEntity {

    data class EmbeddedLink(
        val `class`: List<String>? = null,
        val rel: List<String>,
        val href: URI,
        val type: MediaType? = null,
        val title: String? = null
    ) : SubEntity()


    data class EmbeddedSubEntity<T>(
        val `class`: List<String>? = null,
        val rel: List<String>,
        val properties: T? = null,
        val entities: List<SubEntity>? = null,
        val actions: List<ActionModel>? = null,
        val links: List<LinkModel>? = null
    ) : SubEntity()
}
