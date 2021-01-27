package es.amunoz.tablegamers.models

import com.google.firebase.Timestamp

data class Event (
    var id: String ="",
    var title: String ="",
    var create_by: String ="",
    var date: Timestamp = Timestamp.now(),
    var date_creation: Timestamp = Timestamp.now(),
    var description: String ="",
    var max_people: Int = 0,
    var type: Int = 0,
    var users: List<String> = listOf<String>(),
    var users_confirm: List<String> = listOf<String>()

    )