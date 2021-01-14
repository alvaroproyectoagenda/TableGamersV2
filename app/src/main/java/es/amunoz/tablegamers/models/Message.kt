package es.amunoz.tablegamers.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference

class Message (
    var id: String = "",
    var date: Timestamp = Timestamp.now(),
    var user: DocumentReference?,
    var message: String=""
)