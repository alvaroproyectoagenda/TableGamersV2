package es.amunoz.tablegamers.utils

import android.text.TextUtils

class ValidatorUtil {
    companion object {
        /**
         * Metodo qu
         * @return String con id generada
         */
        fun valEmpty(str: String): Boolean{
            return TextUtils.isEmpty(str) || str == ""
        }
    }
}