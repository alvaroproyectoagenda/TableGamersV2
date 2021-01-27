package es.amunoz.tablegamers.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.*
import androidx.databinding.adapters.Converters
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import com.google.firebase.Timestamp
import com.google.firebase.storage.FirebaseStorage
import es.amunoz.tablegamers.R
import java.text.SimpleDateFormat
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
    fun getDayOfDate(date: Date): String{
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date

        return calendar.get(Calendar.DAY_OF_MONTH).toString()
    }
        fun getMonthName(date: Date):String{
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = date

            when(calendar.get(Calendar.MONTH)+1){
                1 -> return "Enero"
                2 -> return "Febrero"
                3 -> return "Marzo"
                4 -> return "Abril"
                5 -> return "Mayo"
                6 -> return "Junio"
                7 -> return "Julio"
                8 -> return "Agosto"
                9 -> return "Septiembre"
                10 -> return "Octubre"
                11 -> return "Noviembre"
                12 -> return "Diciembre"

            }
            return  "-";
        }
        fun getInfoState(state: String, context: Context): Int{
            when(state){
                Constants.STATES_ARRAY[0] -> {
                    return context.resources.getColor(
                        R.color.green_500, context.theme
                    )
                }
                Constants.STATES_ARRAY[1] -> {
                    return context.resources.getColor(
                        R.color.amber_700, context.theme
                    )
                }
                Constants.STATES_ARRAY[2] -> {
                    return context.resources.getColor(
                        R.color.red_500, context.theme
                    )
                }
            }

            return context.resources.getColor(R.color.white, context.theme)
        }

        fun getDateToString(date: Timestamp): String{
            val sfd = SimpleDateFormat("dd/MM/yyyy")
            return sfd.format(date.toDate())
        }

        fun loadImageFromStorage(avatar: String, context: Context, imageView: ImageView){

            FirebaseStorage.getInstance().getReferenceFromUrl(avatar).downloadUrl.addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Glide.with(context).load(task.result).into(imageView)
                }
            }
        }
        @SuppressLint("UseCompatLoadingForDrawables")
        fun getDrawableAvatar(tag: String, context: Context): Drawable? {

            when(tag){
                "avatar1.png" -> return context.resources.getDrawable(
                    R.drawable.avatar1,
                    context.theme
                )
                "avatar2.png" -> return context.resources.getDrawable(
                    R.drawable.avatar2,
                    context.theme
                )
                "avatar3.png" -> return context.resources.getDrawable(
                    R.drawable.avatar3,
                    context.theme
                )
                "avatar4.png" -> return context.resources.getDrawable(
                    R.drawable.avatar4,
                    context.theme
                )
                "avatar5.png" -> return context.resources.getDrawable(
                    R.drawable.avatar5,
                    context.theme
                )
                "avatar6.png" -> return context.resources.getDrawable(
                    R.drawable.avatar6,
                    context.theme
                )

            }
        return null
        }

        fun setAvatarImage(avatar: String, context: Context, imageView: ImageView){
               val drawable = getDrawableAvatar(avatar, context)
               Glide.with(context).load(drawable).into(imageView)

        }


        @SuppressLint("ResourceType")
        fun setColorChip(type: String, chip: Chip, context: Context){
            when(type){
                context.resources.getString(R.string.nuevo) -> {
                    if (!chip.isChecked) {
                        chip.chipBackgroundColor = Converters.convertColorToColorStateList(
                            getInfoState(
                                type,
                                context
                            )
                        )
                        chip.setTextColor(Color.WHITE)
                    } else {
                        chip.chipBackgroundColor =
                            Converters.convertColorToColorStateList(R.color.grey_300)
                        chip.setTextColor(Color.BLACK)
                    }
                }
                context.resources.getString(R.string.poco_usado) -> {
                    if (!chip.isChecked) {
                        chip.chipBackgroundColor = Converters.convertColorToColorStateList(
                            getInfoState(
                                type,
                                context
                            )
                        )
                        chip.setTextColor(Color.WHITE)
                    } else {
                        chip.chipBackgroundColor =
                            Converters.convertColorToColorStateList(R.color.grey_300)
                        chip.setTextColor(Color.BLACK)
                    }
                }
                context.resources.getString(R.string.antiguo) -> {
                    if (!chip.isChecked) {
                        chip.chipBackgroundColor = Converters.convertColorToColorStateList(
                            getInfoState(
                                type,
                                context
                            )
                        )
                        chip.setTextColor(Color.WHITE)
                    } else {
                        chip.chipBackgroundColor =
                            Converters.convertColorToColorStateList(R.color.grey_300)
                        chip.setTextColor(Color.BLACK)
                    }
                }
            }
        }


        fun getPositionOfArrayData(type: String, value: String, context: Context): Int{
            when(type){
                "poblation" -> {
                    val arr = context.resources.getStringArray(R.array.poblaciones)
                    return arr.indexOf(value)
                }
                "province" -> {
                    val arr = context.resources.getStringArray(R.array.provincias)
                    return arr.indexOf(value)
                }
                "state" -> {
                    val arr = context.resources.getStringArray(R.array.states)
                    return arr.indexOf(value)
                }
            }

            return -1
        }
    }


}