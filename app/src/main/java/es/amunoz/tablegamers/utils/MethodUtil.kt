package es.amunoz.tablegamers.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import es.amunoz.tablegamers.R
import java.util.*

class MethodUtil {
    companion object {
        /**
         * Metodo que genera una id random grancias a la clase UUID
         * @return String con id generada
         */
        fun generateID(): String{
            return UUID.randomUUID().toString()
        }


         fun loadAvatarImage(avatar: String, context: Context, imageView: ImageView){
            FirebaseStorage.getInstance().getReferenceFromUrl(avatar).downloadUrl.addOnCompleteListener {
                    task ->
                if (task.isSuccessful){
                    Glide.with(context).load(task.result).into(imageView)
                }
            }
        }
    }
}