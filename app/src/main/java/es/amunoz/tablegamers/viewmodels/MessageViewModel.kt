package es.amunoz.tablegamers.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import es.amunoz.tablegamers.models.Ad
import es.amunoz.tablegamers.models.Message
import es.amunoz.tablegamers.models.User
import es.amunoz.tablegamers.models.UserMessageAd
import es.amunoz.tablegamers.utils.Constants

class MessageViewModel: ViewModel() {

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()
    val listChat: MutableLiveData<List<Message>> =  MutableLiveData()
    val listUserMessage: MutableLiveData<List<User>> =  MutableLiveData()
    val isResponseMessage: MutableLiveData<Boolean> =  MutableLiveData()

    init{
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun getUser():String{
        return auth.currentUser?.uid!!
    }
    fun responseMessage(
        msg: Message,
        idAd: String,
        idUser: String)
    {
        firestore.collection("messages").document(idAd).collection(idUser).document(msg.id).set(msg)
            .addOnCompleteListener {
                isResponseMessage.value = it.isSuccessful
        }.addOnFailureListener {

                isResponseMessage.value = false
        }
    }
    fun callChat(
    idAd: String,
    idUser: String
    ) {

        firestore.collection("messages").document(idAd).collection(idUser).orderBy("date",Query.Direction.ASCENDING).addSnapshotListener { documents, error ->
            var listMessageCache = mutableListOf<Message>()
            if (documents != null) {
                for (document in documents) {

                    var message = document.toObject(Message::class.java)

                    listMessageCache.add(message)
                }
            }
            listChat.value = listMessageCache


        }
          /*  .addOnSuccessListener { documents ->

                var listMessageCache = mutableListOf<Message>()
                for (document in documents) {

                    var message = document.toObject(Message::class.java)

                    listMessageCache.add(message)
                }
                listChat.value = listMessageCache



            }
            .addOnFailureListener { exception ->
                listChat.value = null
                Log.w(Constants.TAG_ERROR, "Error getting documents: ", exception)
            }*/
    }
    fun callUserMessage(
        idAd: String
    ) {

        firestore.collection("users_messages_ads").document(idAd).get()
            .addOnSuccessListener { document ->

                if (document.exists()) {

                    val userMessageAd = document.toObject(UserMessageAd::class.java)
                    val listUserMessageCache: MutableList<User> = mutableListOf()
                    val arrayUsersMessage = userMessageAd?.users!!

                    if (arrayUsersMessage.isNotEmpty()) {


                        arrayUsersMessage.forEachIndexed { index, userId ->
                            firestore.collection("users").document(userId).get()
                                .addOnSuccessListener { user ->
                                    val u = user.toObject(User::class.java)
                                    listUserMessageCache.add(u!!)
                                    if (index == arrayUsersMessage.size - 1) {
                                        listUserMessage.value = listUserMessageCache
                                    }
                                }.addOnFailureListener {
                                Log.i("nino_e", it.message)
                            }
                        }


                    } else {
                        listUserMessage.value = listUserMessageCache

                    }
                }else{
                    listUserMessage.value = mutableListOf()
                }
                }
                    .addOnFailureListener { exception ->
                        //  listAds.value = null
                        Log.w(Constants.TAG_ERROR, "Error getting documents: ", exception)
                    }


    }
}