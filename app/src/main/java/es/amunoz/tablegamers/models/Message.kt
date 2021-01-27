package es.amunoz.tablegamers.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

data class Message (
    var id: String = "",
    var date: Timestamp = Timestamp.now(),
    var user: String="",
    var message: String=""
)