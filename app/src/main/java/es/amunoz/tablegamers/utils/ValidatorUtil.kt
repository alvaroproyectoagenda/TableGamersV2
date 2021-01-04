package es.amunoz.tablegamers.utils

import android.text.TextUtils
import android.util.Patterns

class ValidatorUtil {
    companion object {
        /**
         * Metodo qu
         * @return String con id generada
         */
        fun isEmpty(str: String): Boolean{
            return TextUtils.isEmpty(str) || str == ""
        }
        fun isEmail(str: String): Boolean{
            return if (!isEmpty(str)){
                Patterns.EMAIL_ADDRESS.matcher(str).matches();
            }else{
                false
            }

        }
        fun isNumber(str: String): Boolean{
            return !TextUtils.isDigitsOnly(str) || str == ""
        }
        fun isPhone(str: String): Boolean{
            return if (!isEmpty(str)){
                Patterns.PHONE.matcher(str).matches();
            }else{
                false
            }

        }

        fun isPassoword(str: String): Boolean{
            return if (!isEmpty(str)){
                str.length >=6
            }else{
                false
            }

        }

        fun areEqualPassword(str: String, str2: String): Boolean{
            return if (!isEmpty(str) && (!isEmpty(str2))){
                str == str2
            }else{
                false
            }

        }
    }
}