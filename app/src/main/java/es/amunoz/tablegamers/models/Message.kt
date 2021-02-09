package es.amunoz.tablegamers.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Message(
    var id: String = "",
    //var date: Timestamp = Timestamp.now(),
    @ServerTimestamp
    val date: Date? = null,
    var user: String = "",
    var message: String = ""
)