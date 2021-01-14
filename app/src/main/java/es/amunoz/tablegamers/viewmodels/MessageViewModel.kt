package es.amunoz.tablegamers.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import es.amunoz.tablegamers.models.Message

class MessageViewModel: ViewModel() {

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()
    val listAds: MutableLiveData<List<Message>> =  MutableLiveData()


    init{
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }
}