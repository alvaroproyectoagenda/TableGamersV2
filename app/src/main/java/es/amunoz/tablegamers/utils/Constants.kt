package es.amunoz.tablegamers.utils

class Constants {

    companion object{
        const val SPLASH_SCREEN_TIME: Long = 3000
        const val CHAT_TIME: Long = 6000

        const val TAG_ERROR: String   = "ERROR"
        const val PATH_STORAGE_DEFAULT_AVATAR ="default_avatar.png";
        const val EXTRA_ID_AD = "EXTRA_ID_AD"
        const val EXTRA_ID_USER = "EXTRA_ID_USER"
        const val EXTRA_ID_USER_CREATE = "EXTRA_ID_USER_CREATE"
        const val EXTRA_ID_EVT = "EXTRA_ID_AD"
        val STATES_ARRAY = listOf("Nuevo", "Poco usado", "Antiguo")
        const val ONE_MEGABYTE: Long = 1024 * 1024
        const val NOT_FOUND_IMAGE_AD = "gs://tablegamers-ecc11.appspot.com/ads/notfound.png"
        const val GS_FIRESTORE_AD_PATH = "gs://tablegamers-ecc11.appspot.com/ads/";
        const val MAX_IMAGES = 3
        const val PICK_IMAGE_REQUEST = 1
        const val VIEW_TYPE_CHAT_ME = 1
        const val VIEW_TYPE_CHAT_OTHER = 2
        const val TYPE_EVENT_PUBLIC = 1
        const val TYPE_EVENT_PRIVATE = 2
        const val MAX_CHARSET_DESCRIPTIONS_EVENT = 600
        const val MAX_CHARSET_DESCRIPTIONS_AD = 245
    }
}