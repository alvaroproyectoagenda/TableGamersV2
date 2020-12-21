package es.amunoz.tablegamers.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import es.amunoz.tablegamers.models.User
import es.amunoz.tablegamers.utils.Constants.Companion.TAG_ERROR

class UserViewModel: ViewModel() {

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    val isAddUser: MutableLiveData<Boolean> =  MutableLiveData()


    init{
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }
    /**
     * Agregamos un usuario a la base de datos de Firestore.
     * @return True o False en función de si se ha insertado correctamenteo no.
     * @param[user] Objeto de tipo User que vamos a insertar
     */
    fun add(
        user: User
    ) {

        var idUser: String? = user.id

        if(idUser != null){
            firestore.collection("users").document(idUser).set(user)
                .addOnSuccessListener {
                    isAddUser.value = true
                }
                .addOnFailureListener {
                    isAddUser.value = false
                }
        }

    }
}