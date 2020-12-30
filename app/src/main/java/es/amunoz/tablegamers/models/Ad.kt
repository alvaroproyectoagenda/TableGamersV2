package es.amunoz.tablegamers.models

import com.google.firebase.Timestamp

data class Ad (
    var id: String = "",
    var create_for: String ="",
    var date_creation: Timestamp = Timestamp.now(),
    var description: String="",
    var images: List<String> = emptyList(),
    var poblation: String="",
    var price: Int = 0,
    var province: String="",
    var state: String="",
    var title: String=""
        )