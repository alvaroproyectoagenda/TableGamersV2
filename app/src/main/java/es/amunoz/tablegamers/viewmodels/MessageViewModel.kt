package es.amunoz.tablegamers.viewmodels

import android.content.Intent
import android.os.Handler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import es.amunoz.tablegamers.NavigationMenuActivity
import es.amunoz.tablegamers.models.Ad
import es.amunoz.tablegamers.models.Message
import es.amunoz.tablegamers.models.User
import es.amunoz.tablegamers.models.UserMessageAd
import es.amunoz.tablegamers.utils.Constants
import kotlin.concurrent.thread

class MessageViewModel: ViewModel() {

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()
    val listChat: MutableLiveData<List<Message>> =  MutableLiveData()
    val listUserMessage: MutableLiveData<List<User>> =  MutableLiveData()
    val isResponseMessage: MutableLiveData<Boolean> =  MutableLiveData()
    val messageException: MutableLiveData<String> =  MutableLiveData()

    init{
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    fun getUser():String
    {
        return auth.currentUser?.uid!!
    }
    fun responseMessage(
        msg: Message,
        idAd: String,
        idUser: String)
    {

        var mapValues = mapOf<String, Any>(
            "id" to msg.id,
            "message" to msg.message,
            "user" to msg.user,
            "date" to FieldValue.serverTimestamp()
        )
        firestore.collection("messages").document(idAd).collection(idUser).document(msg.id).set(mapValues)
            .addOnCompleteListener {
                isResponseMessage.value = it.isSuccessful
        }.addOnFailureListener {
                isResponseMessage.value = false
                messageException.value = it.message
        }
    }
    fun callChat(
    idAd: String,
    idUser: String
    ) {


        firestore.collection("messages").document(idAd).collection(idUser).orderBy("date",Query.Direction.ASCENDING)


            .addSnapshotListener { documents, error ->


            var listMessageCache = mutableListOf<Message>()
            if (documents != null) {
                for (document in documents) {

                    var message = document.toObject(Message::class.java)

                    listMessageCache.add(message)
                }
            }else{
                var user = getUser()
                if(idUser != user){
                    callChat(idAd,user)
                }
            }


                    listChat.value = listMessageCache




        }

    }
    fun callUserMessage(
        idAd: String
    ) {
        val userUid =auth.currentUser?.uid

        firestore.collection("users_messages_ads").document(idAd).get()
            .addOnSuccessListener { document ->

                if (document.exists()) {

                    Handler().postDelayed({


                    val userMessageAd = document.toObject(UserMessageAd::class.java)
                    val listUserMessageCache: MutableList<User> = mutableListOf()
                    val arrayUsersMessage = userMessageAd?.users!!

                    if (arrayUsersMessage.isNotEmpty()) {


                        arrayUsersMessage.forEachIndexed { index, userId ->


                                firestore.collection("users").document(userId).get()
                                    .addOnSuccessListener { user ->
                                        val u = user.toObject(User::class.java)
                                        if (userUid!=null && u!=null && userUid!=u.id) {
                                            listUserMessageCache.add(u)
                                        }
                                        if (index == arrayUsersMessage.size - 1) {



                                            listUserMessage.value = listUserMessageCache
                                        }
                                    }.addOnFailureListener {
                                        Log.i("TAG", it.message)
                                    }

                        }


                    } else {
                        listUserMessage.value = listUserMessageCache

                    }

                    }, Constants.CHAT_TIME)
                }else{
                    listUserMessage.value = mutableListOf()
                }
                }
                    .addOnFailureListener { exception ->
                        //  listAds.value = null
                        Log.w(Constants.TAG_ERROR, "Error getting documents: ", exception)
                    }


    }
    fun createChat(
        msg: Message,
        idAd: String,
        idUser: String )
    {


        firestore.collection("users_messages_ads").document(idAd).update(
            "users", FieldValue.arrayUnion(idUser))
            .addOnCompleteListener {
                if(it.isSuccessful){
                    responseMessage(msg, idAd, idUser)
                }else{
                    isResponseMessage.value = false
                    messageException.value = "Error create"
                }
            }.addOnFailureListener {

                isResponseMessage.value = false
                messageException.value = it.message
            }
    }
}