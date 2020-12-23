package es.amunoz.tablegamers.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.ktx.Firebase
import es.amunoz.tablegamers.models.User
import es.amunoz.tablegamers.utils.Constants.Companion.TAG_ERROR
import java.lang.Exception
import java.lang.reflect.Executable

class UserViewModel: ViewModel() {

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()


    val isAddUser: MutableLiveData<Boolean> =  MutableLiveData()
    val existNickname: MutableLiveData<Boolean> =  MutableLiveData()
    val emailForgotSend: MutableLiveData<Boolean> =  MutableLiveData()
    val firebaseUser: MutableLiveData<FirebaseUser> =  MutableLiveData()
    val messageExceptionRegisterUser: MutableLiveData<String> =  MutableLiveData()
    val user: MutableLiveData<User> =  MutableLiveData()


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
                    messageExceptionRegisterUser.value = "Error al registrar el usuario"
                }
        }

    }
    /**
     * Comprobamos que el nickname esté disponible
     * @param[nickname] String del nickname que tenemos que comprarar
     */
    fun checkNickname(
        nickname: String
    ) {

            firestore.collection("users").whereEqualTo("nickname",nickname).get()
                .addOnSuccessListener { documents ->
                    existNickname.value = documents.size()!=0
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG_ERROR, "Error getting documents: ", exception)
                }
    }
    /**
     * Comprobamos que el nickname esté disponible
     * @param[nickname] String del nickname que tenemos que comprarar
     */
    fun getUser(
        id: String
    ) {

        firestore.collection("users").document(id).get()
            .addOnSuccessListener { document ->
               user.value = document.toObject(User::class.java)
            }
            .addOnFailureListener { exception ->
                user.value = null
                Log.w(TAG_ERROR, "Error getting documents: ", exception)
            }
    }
    /**
     * Login user
     * @param[user] Objeto de tipo User que vamos a comprobar para loguear
     */
    fun loginUser(
        email: String,
        pass: String
    ) {

        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseUser.value = auth.currentUser
            } else {
                firebaseUser.value = null
            }
        }

    }

    /**
     * Registro user en Authentication Firesbase
     * @param[user] Objeto de tipo User que vamos a comprobar para loguear
     */
    fun registerUserAuth(
        email: String,
        pass: String,
        user: User
    ) {

        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseUser.value = auth.currentUser
                    user.id = firebaseUser.value?.uid.toString()
                    add(user)
                } else {
                     when(task.exception){
                         is FirebaseAuthWeakPasswordException -> {messageExceptionRegisterUser.value="Contraseña débil. Recuerda mínimo 6 digitos"}
                         is FirebaseAuthInvalidCredentialsException -> {messageExceptionRegisterUser.value="Formato de email incorrecto"}
                         is FirebaseAuthUserCollisionException -> {messageExceptionRegisterUser.value="Ya existe un usuario con ese email"}
                         is Exception -> {messageExceptionRegisterUser.value="¡Ha ocurrido un error! Vuelve a intentarlo"}
                     }
                    isAddUser.value = false

                }


            }

    }
    /**
     * He olvidado contraseña
     * @param[email] email al que tenemos que enviar
     */
    fun fogotPassword(
        email: String
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                emailForgotSend.value = task.isSuccessful
            }


    }
}