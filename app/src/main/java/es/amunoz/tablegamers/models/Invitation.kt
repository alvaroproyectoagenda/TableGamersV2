package es.amunoz.tablegamers.models

data class Invitation(
    var id: String ="",
    var invitations: List<String> = listOf<String>()
)
