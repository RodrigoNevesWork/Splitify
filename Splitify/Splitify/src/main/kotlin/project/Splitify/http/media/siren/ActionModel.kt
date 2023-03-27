package project.splitify.http.media.siren

import java.net.URI

data class ActionModel(
    val name: String,
    val title: String,
    val method: String,
    val href: URI,
)