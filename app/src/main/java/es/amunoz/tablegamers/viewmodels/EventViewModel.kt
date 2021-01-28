package es.amunoz.tablegamers.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import es.amunoz.tablegamers.models.Ad
import es.amunoz.tablegamers.models.Event
import es.amunoz.tablegamers.models.Message
import es.amunoz.tablegamers.utils.Constants

class EventViewModel : ViewModel() {

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()
    val listEvents: MutableLiveData<List<Event>> =  MutableLiveData()
    val event: MutableLiveData<Event> =  MutableLiveData()
    val isDeleteEvt: MutableLiveData<Boolean> =  MutableLiveData()
    val isUpdateUserEvent: MutableLiveData<Boolean> =  MutableLiveData()
    val messageException: MutableLiveData<String> =  MutableLiveData()


    init{
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    /**
     * Obtenemos los eventos
     *
     */
    fun callPublicEvent(

    ) {

        firestore.collection("events").whereEqualTo("type",Constants.TYPE_EVENT_PUBLIC).get()
            .addOnSuccessListener { documents ->
                var adsListTemp = arrayListOf<Event>()
                for (document in documents) {
                    var event = document.toObject(Event::class.java)
                    adsListTemp.add(event)
                }
            listEvents.value = adsListTemp;


            }
            .addOnFailureListener { exception ->
                listEvents.value = null;
                Log.w(Constants.TAG_ERROR, "Error getting documents: ", exception)
            }
    }
    /**
     * Obtenemos los eventos
     *
     */
    fun callMyEvents(

    ) {

        firestore.collection("events").whereEqualTo("create_by", auth.currentUser?.uid).get()
            .addOnSuccessListener { documents ->
                var adsListTemp = arrayListOf<Event>()
                for (document in documents) {
                    var event = document.toObject(Event::class.java)
                    adsListTemp.add(event)
                }
                listEvents.value = adsListTemp;


            }
            .addOnFailureListener { exception ->
                listEvents.value = null;
                Log.w(Constants.TAG_ERROR, "Error getting documents: ", exception)
            }
    }

    /**
     * Borramos evento
     *
     */
    fun deleteEvent(
        idEvt: String
    ) {

        firestore.collection("events").document(idEvt).delete()
            .addOnSuccessListener {
                isDeleteEvt.value = true
            }
            .addOnFailureListener { exception ->
                isDeleteEvt.value = false
                messageException.value = exception.message
                Log.i("err",exception.message)
            }
    }
    /**
     * Obtener evento
     *
     */
    fun callEvent(
        idEvt: String
    ) {

        firestore.collection("events").document(idEvt).get()
            .addOnSuccessListener {

                event.value = it.toObject(Event::class.java)
            }
            .addOnFailureListener { exception ->
                event.value = null
                messageException.value = exception.message
                Log.i("err",exception.message)
            }
    }

    fun getCurrentUserUID(): String{
        return auth.uid!!
    }

    /**
     * Obtener evento
     *
     */
    fun userGoToEvent(
        evt: Event
    ) {
        firestore.collection("events").document(evt.id).update(
            mapOf(
                "users" to evt.users,
                "users_confirm" to evt.users_confirm
            )
        ).addOnSuccessListener {
            isUpdateUserEvent.value = true
        }.addOnFailureListener {
            isUpdateUserEvent.value = false
            messageException.value = it.message
        }

    }
}