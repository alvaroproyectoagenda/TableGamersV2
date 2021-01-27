package es.amunoz.tablegamers.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import es.amunoz.tablegamers.models.Ad
import es.amunoz.tablegamers.models.Message
import es.amunoz.tablegamers.models.User
import es.amunoz.tablegamers.models.UserMessageAd
import es.amunoz.tablegamers.utils.Constants

class MessageViewModel: ViewModel() {

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()
    val listAds: MutableLiveData<List<Message>> =  MutableLiveData()
    val listUserMessage: MutableLiveData<List<User>> =  MutableLiveData()

    init{
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    /**
     * Obtenemos los usuarios con los que tienes mensajes
     *
     */
    fun callMessage(
    idAd: String
    ) {

        firestore.collection("messages").document(idAd).collection("KbVw2vFDtDXcBh8LFWKXSftPKZR2").get()
            .addOnSuccessListener { documents ->

                for (document in documents) {
                    var message = document.toObject(Message::class.java)
                    Log.i("asdas","asdasd");
                }



            }
            .addOnFailureListener { exception ->
              //  listAds.value = null
                Log.w(Constants.TAG_ERROR, "Error getting documents: ", exception)
            }
    }
    fun callUserMessage(
        idAd: String
    ) {

        firestore.collection("users_messages_ads").document(idAd).get()
            .addOnSuccessListener { document ->


                val userMessageAd = document.toObject(UserMessageAd::class.java)
                val listUserMessageCache: MutableList<User> = mutableListOf()
                    for(userId in userMessageAd?.users!!){
                        firestore.collection("users").document(userId).get().addOnSuccessListener { user ->
                           val u = user.toObject(User::class.java)
                           listUserMessageCache.add(u!!)
                        }.addOnFailureListener {
                            Log.i("nino_e",it.message)
                        }
                    }

                listUserMessage.value = listUserMessageCache

            }
            .addOnFailureListener { exception ->
                //  listAds.value = null
                Log.w(Constants.TAG_ERROR, "Error getting documents: ", exception)
            }
    }
}