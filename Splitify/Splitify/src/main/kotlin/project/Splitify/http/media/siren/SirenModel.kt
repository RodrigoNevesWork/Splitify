package project.splitify.http.media.siren

data class SirenModel<T>(
    val clazz: List<String>? = null,
    val properties: T? = null,
    val entities: List<SubEntity>? = null,
    val actions: List<ActionModel>? = null,
    val links: List<LinkModel>? = null,
    val title: String? = null
) {
    companion object {
        private const val APPLICATION_TYPE = "application"
        private const val SIREN_SUBTYPE = "vnd.siren+json"
        const val SIREN_MEDIA_TYPE = "$APPLICATION_TYPE/$SIREN_SUBTYPE"
    }
}