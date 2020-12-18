package es.amunoz.tablegamers.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import es.amunoz.tablegamers.models.User

class UserViewModel: ViewModel() {

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

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
    ): Boolean {

        var res: Boolean = false
        // firestore.collection("users").document(user.id).set(user)
        firestore.collection("users").document("ase").set(user)
            .addOnSuccessListener {
                res = true
            }
            .addOnFailureListener {
                res = false;
            }
        return res;
    }
}