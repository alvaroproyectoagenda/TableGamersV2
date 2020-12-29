package es.amunoz.tablegamers.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
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


         fun loadImageFromStorage(avatar: String, context: Context, imageView: ImageView){
            FirebaseStorage.getInstance().getReferenceFromUrl(avatar).downloadUrl.addOnCompleteListener {
                    task ->
                if (task.isSuccessful){
                    Glide.with(context).load(task.result).into(imageView)
                }
            }
        }
        @SuppressLint("UseCompatLoadingForDrawables")
        fun getDrawableAvatar(tag: String, context: Context,): Drawable? {

            when(tag){
                "avatar1.png" -> return context.resources.getDrawable(R.drawable.avatar1, context.theme)
                "avatar2.png" -> return context.resources.getDrawable(R.drawable.avatar2, context.theme)
                "avatar3.png" -> return context.resources.getDrawable(R.drawable.avatar3, context.theme)
                "avatar4.png" -> return context.resources.getDrawable(R.drawable.avatar4, context.theme)
                "avatar5.png" -> return context.resources.getDrawable(R.drawable.avatar5, context.theme)
                "avatar6.png" -> return context.resources.getDrawable(R.drawable.avatar6, context.theme)

            }
        return null
        }

        fun setAvatarImage(avatar: String, context: Context, imageView: ImageView){
               val drawable = getDrawableAvatar(avatar, context)
               Glide.with(context).load(drawable).into(imageView)

        }

    }
}