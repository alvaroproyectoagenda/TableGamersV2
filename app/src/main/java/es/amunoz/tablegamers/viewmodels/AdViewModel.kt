package es.amunoz.tablegamers.viewmodels

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
    fun getAds(): MutableLiveData<List<Ad>> {
        return this.listAds
    }
}