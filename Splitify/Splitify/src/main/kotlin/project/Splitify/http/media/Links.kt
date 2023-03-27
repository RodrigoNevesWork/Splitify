package project.splitify.http.media

import project.splitify.http.media.siren.LinkModel
import java.net.URI

object Links {
    fun self(href : URI) = LinkModel(
        rel = listOf(Rels.SELF),
        href = href
    )

    val home = LinkModel(
        rel = listOf(Rels.HOME),
        href = Uris.home()
    )

    val userHome = LinkModel(
        rel = listOf(Rels.USER_HOME),
        href = Uris.userHome()
    )

}