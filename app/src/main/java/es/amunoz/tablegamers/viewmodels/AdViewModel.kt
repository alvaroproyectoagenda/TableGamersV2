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
import es.amunoz.tablegamers.utils.Filter

class AdViewModel: ViewModel() {

    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()


    val listAds: MutableLiveData<List<Ad>> =  MutableLiveData()
    val myAd: MutableLiveData<Ad> =  MutableLiveData()
    val isAddAd: MutableLiveData<Boolean> =  MutableLiveData()
    val isUpdateAd: MutableLiveData<Boolean> =  MutableLiveData()
    val isDeleteAd: MutableLiveData<Boolean> =  MutableLiveData()
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
     * Obtenemos los anuncios de la base de datos del usuario
     *
     */
    fun callAdsUser(
    idUser: String
    ) {

        firestore.collection("ads").whereEqualTo("create_for",idUser).get()
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
    fun callAdsFilter(filter: Filter

    ) {
        var collectionRef  = firestore.collection("ads")
        var query = collectionRef.whereEqualTo("province",filter.province)

        if(!filter.title.isNullOrBlank()){
            query = collectionRef.whereEqualTo("title",filter.title)
        }
        if(filter.price_since != 0 && filter.price_until == 0){
            query = collectionRef.whereGreaterThanOrEqualTo("price",filter.price_since!!).whereLessThanOrEqualTo("price", filter.price_until!!)
        }


       query.get()
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
    /**
     * Borramos anuncio
     *
     */
    fun deleteAd(
        idAd: String
    ) {

        firestore.collection("ads").document(idAd).delete()
            .addOnSuccessListener {
                isDeleteAd.value = true
            }
            .addOnFailureListener { exception ->
                isDeleteAd.value = false
                messageException.value = exception.message
                Log.i("err",exception.message)
            }
    }
    /**
     * Guardamos el anuncio en bd
     *
     */
    fun saveAd(
        ad: Ad
    ) {

        firestore.collection("ads").document(ad.id).set(ad)
            .addOnSuccessListener {
                isAddAd.value = true
            }
            .addOnFailureListener {
                isAddAd.value = false
                messageException.value = "Error al registrar el usuario"
            }
    }
    /**
     * Modificados el anuncio en bd
     *
     */
    fun updateAd(
        ad: Ad
    ) {

        firestore.collection("ads").document(ad.id).set(ad)
            .addOnSuccessListener {
                isUpdateAd.value = true
            }
            .addOnFailureListener {
                isUpdateAd.value = false
                messageException.value = "Error al registrar el usuario"
            }
    }




}