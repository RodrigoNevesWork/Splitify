package project.splitify.http.media.siren

import java.net.URI

data class LinkModel(
    val rel: List<String>,
    val href: URI,
)