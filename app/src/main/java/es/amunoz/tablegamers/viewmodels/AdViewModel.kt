package es.amunoz.tablegamers.viewmodels

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import es.amunoz.tablegamers.models.Ad
import es.amunoz.tablegamers.models.User
import es.amunoz.tablegamers.utils.Constants

class AdViewModel: ViewModel() {

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()


    val listAds: MutableLiveData<List<Ad>> =  MutableLiveData()
    val myAd: MutableLiveData<Ad> =  MutableLiveData()
    val messageException: MutableLiveData<String> =  MutableLiveData()


    init {
        firestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
    }

    /**
     * Obtenemos los anuncios de la base de datos
     *
     */
    fun callAds(

    ) {

        firestore.collection("ads").get()
            .addOnSuccessListener { documents ->
                var adsListTemp = arrayListOf<Ad>()
                for (document in documents) {
                    var ad = document.toObject(Ad::class.java)
                    adsListTemp.add(ad)
                }
                listAds.value = adsListTemp

            }
            .addOnFailureListener { exception ->
                listAds.value = null
                Log.w(Constants.TAG_ERROR, "Error getting documents: ", exception)
            }
    }
    /**
     * Obtenemos los anuncios de la base de datos
     *
     */
    fun getAdByID(
        idAd: String
    ) {

        firestore.collection("ads").document(idAd).get()
            .addOnSuccessListener { document ->
                myAd.value = document.toObject(Ad::class.java)
            }
            .addOnFailureListener { exception ->
                myAd.value = null
                messageException.value = exception.message
                Log.i("err",exception.message)
            }
    }


}