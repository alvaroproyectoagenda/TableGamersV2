package es.amunoz.tablegamers.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import es.amunoz.tablegamers.models.Event
import es.amunoz.tablegamers.models.Invitation
import es.amunoz.tablegamers.utils.Constants

class InvitationViewModel: ViewModel() {

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()
    val messageException: MutableLiveData<String> =  MutableLiveData()
    val isAddInvitation: MutableLiveData<Boolean> =  MutableLiveData()
    val isUpdateInvitation: MutableLiveData<Boolean> =  MutableLiveData()
    val listEvents: MutableLiveData<List<Event>> =  MutableLiveData()


    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    /**
     * CREAMOS invitacion
     *
     */
    fun addInvitation(
        idEvent: String,
        idUser: String
    ) {

        firestore.collection("invitations").document(idUser).update("invitations",FieldValue.arrayUnion(idEvent))
            .addOnSuccessListener {
                isAddInvitation.value = true
            }
            .addOnFailureListener { exception ->
                isAddInvitation.value = false
                messageException.value = exception.message
                Log.i("err", exception.message)
            }

    }
    /**
     * CREAMOS invitacion
     *
     */
    fun deleteInvitation(
        invitationID: String
    ) {
        val user =auth.currentUser?.uid
        if (user != null) {
            firestore.collection("invitations").document(user)
                .update("invitations", FieldValue.arrayRemove(invitationID))
                .addOnSuccessListener {
                    isUpdateInvitation.value = true
                }
                .addOnFailureListener { exception ->
                    isUpdateInvitation.value = false
                    messageException.value = exception.message
                    Log.i("err", exception.message)
                }
        }

    }
    /*
    Update Event after invitation
    */
    fun updateEventAceptInvitation(
        event: Event
    ) {
        val user =auth.currentUser?.uid
        if (user != null) {
            firestore.collection("events").document(event.id)
                .update("users_confirm", FieldValue.arrayUnion(user))
                .addOnSuccessListener {
                    deleteInvitation(event.id)
                }
                .addOnFailureListener { exception ->
                    isUpdateInvitation.value = false
                    messageException.value = exception.message
                    Log.i("err", exception.message)
                }
        }
    }
    fun updateEventRetryInvitation(
       event: Event
    ) {
        val user =auth.currentUser?.uid
        if (user != null) {
            val changeMaxPeople = (event.max_people) - 1
            firestore.collection("events").document(event.id)
                .update(
                    mapOf(
                        "users" to FieldValue.arrayRemove(user),
                        "max_people" to changeMaxPeople
                    )
                )
                .addOnSuccessListener {
                    deleteInvitation(event.id)
                }
                .addOnFailureListener { exception ->

                    isUpdateInvitation.value = false
                    messageException.value = exception.message
                    Log.i("err", exception.message)
                }
        }
    }

    fun callMyInvitations(){


        val user =auth.currentUser?.uid
        if (user != null) {


            firestore.collection("invitations").document(user).get()
                .addOnSuccessListener { document ->

                    if(document.exists()){
                    val inv = document.toObject(Invitation::class.java)

                    val arrayInvitations = inv?.invitations!!
                    val arrayEventsInvitations = arrayListOf<Event>()
                    if(arrayInvitations.isNotEmpty()){
                    arrayInvitations.forEachIndexed { index, event ->

                        firestore.collection("events").document(event).get()
                            .addOnSuccessListener {
                                if(it.exists()){


                                val event = it.toObject(Event::class.java)
                                if (event != null) {
                                    arrayEventsInvitations.add(event)

                                }
                                if (index == arrayInvitations.size - 1) {
                                    listEvents.value = arrayEventsInvitations
                                }

                                }


                            }
                            .addOnFailureListener { exception ->

                                messageException.value = exception.message
                                Log.i("err", exception.message)
                            }

                    }
                }else {
                        listEvents.value = arrayEventsInvitations

                    }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(Constants.TAG_ERROR, "Error getting documents: ", exception)
                }




        }
    }
}