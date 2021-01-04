package es.amunoz.tablegamers.models

import com.google.firebase.Timestamp
import es.amunoz.tablegamers.utils.Constants

data class Ad (
    var id: String = "",
    var create_for: String ="",
    var date_creation: Timestamp = Timestamp.now(),
    var description: String="",
    var images: List<String> = arrayListOf<String>(Constants.NOT_FOUND_IMAGE_AD),
    var poblation: String="",
    var price: Int = 0,
    var province: String="",
    var state: String="",
    var title: String=""
        )