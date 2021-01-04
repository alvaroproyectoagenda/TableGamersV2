package es.amunoz.tablegamers.utils

class Constants {

    companion object{
        const val SPLASH_SCREEN_TIME: Long = 3000
        const val TAG_ERROR: String   = "ERROR"
        const val PATH_STORAGE_DEFAULT_AVATAR ="default_avatar.png";
        const val EXTRA_ID_AD = "EXTRA_ID_AD"
        val STATES_ARRAY = listOf("Nuevo", "Poco usado", "Antiguo")
        const val ONE_MEGABYTE: Long = 1024 * 1024
        const val NOT_FOUND_IMAGE_AD = "gs://tablegamers-ecc11.appspot.com/ads/notfound.png"
        const val GS_FIRESTORE_AD_PATH = "gs://tablegamers-ecc11.appspot.com/ads/";
        const val MAX_IMAGES = 3
        const val PICK_IMAGE_REQUEST = 1
    }
}