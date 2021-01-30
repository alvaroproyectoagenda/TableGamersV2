package es.amunoz.tablegamers.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
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
    val isDelInvitation: MutableLiveData<Boolean> =  MutableLiveData()
    val myInvitation: MutableLiveData<Invitation> =  MutableLiveData()

    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    /**
     * CREAMOS invitacion
     *
     */
    fun addInvitation(
        invitation: Invitation
    ) {

        firestore.collection("invitations").document(invitation.id).set(invitation)
            .addOnSuccessListener {
                isAddInvitation.value = true
            }
            .addOnFailureListener { exception ->
                isAddInvitation.value = false
                messageException.value = exception.message
                Log.i("err",exception.message)
            }
    }
    /**
     * CREAMOS invitacion
     *
     */
    fun deleteInvitation(
        invitation: Invitation
    ) {

        firestore.collection("invitations").document(invitation.id).update(
            mapOf(
                "invitations" to invitation.invitations
            )
        )
            .addOnSuccessListener {
                isDelInvitation.value = true
            }
            .addOnFailureListener { exception ->
                isDelInvitation.value = false
                messageException.value = exception.message
                Log.i("err",exception.message)
            }

        /**
         * Obtenemos los eventos
         *
         */
        fun callMyInvitations(
        ) {
            val user =auth.currentUser?.uid
            if (user != null) {
                firestore.collection("invitations").document(user).get()
                    .addOnSuccessListener { document ->

                        myInvitation.value = document.toObject(Invitation::class.java)




                    }
                    .addOnFailureListener { exception ->
                        Log.w(Constants.TAG_ERROR, "Error getting documents: ", exception)
                    }
            }
        }
    }
}