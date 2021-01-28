package es.amunoz.tablegamers.utils

import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.Converters
import com.google.android.material.chip.Chip
import com.google.firebase.Timestamp
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.models.Ad
import es.amunoz.tablegamers.models.Event
import es.amunoz.tablegamers.models.User
import java.util.*

@BindingAdapter("setHeaderItemImagen")
fun ImageView.setHeaderItemImage(item: Ad){
    MethodUtil.loadImageFromStorage(item.images[0], context, this)
}
@BindingAdapter("setAvatarItemImagen")
fun ImageView.setAvatarItemImagen(item: User){
    setImageDrawable(MethodUtil.getDrawableAvatar(item.avatar!!, context))
}

@BindingAdapter("setEuroSymbol")
fun TextView.setEuroSymbol(item: Ad){
    text =  item.price.toString().plus("€")
}
@BindingAdapter("setLocationName")
fun TextView.setLocationName(item: Ad){
    var location = "${item.poblation} (${item.province}) ";
    text = location
}
@BindingAdapter("setChipState")
fun Chip.setChipState(item: Ad){
    chipBackgroundColor = Converters.convertColorToColorStateList(MethodUtil.getInfoState(item.state,context))
    text = item.state
}
@BindingAdapter("setDateFormat")
fun TextView.setDateFormat(item: Ad){
    text = MethodUtil.getDateToString(item.date_creation)
}
@BindingAdapter("setDateFormat")
fun TextView.setDateFormat(item: Timestamp){
    text = MethodUtil.getDateToString(item)
}
@BindingAdapter("setItemSpinerState")
fun Spinner.setItemSpinerState(item: String?){

   if(item!=null){
    when(tag.toString()) {
        "state"->{
            when(item){
                "Nuevo"-> setSelection(0)
                "Poco usado" -> setSelection(1)
                "Antiguo" -> setSelection(2)
            }

        }
        "province"->{
                setSelection(MethodUtil.getPositionOfArrayData("province",item,context))

        }
        "poblation"->{

                setSelection(MethodUtil.getPositionOfArrayData("poblation", item,context))


        }
    }
    }

}

@BindingAdapter("setItemSpinerTypeEvt")
fun Spinner.setItemSpinerTypeEvt(item: Int?){

    if(item!=null){


        when(item){
            Constants.TYPE_EVENT_PUBLIC-> setSelection(0)
            Constants.TYPE_EVENT_PRIVATE-> setSelection(1)
        }


    }

}

@BindingAdapter("setNumberOfDay")
fun TextView.setNumberOfDay(item: Event){
    var tmp = item.date.toDate()
    text =  MethodUtil.getDayOfDate(tmp)
}
@BindingAdapter("setMonthName")
fun TextView.setMonthName(item: Event){
    var tmp = item.date.toDate()
    text =  MethodUtil.getMonthName(tmp)
}
@BindingAdapter("setMaxPlaces")
fun TextView.setMaxPlaces(item: Event){
    if(item.users.isEmpty()){
        text =  "${item.max_people}/${item.max_people}"
    }else{
        text =  "${(item.max_people -item.users.size  )}/${item.max_people}"
    }

}

@BindingAdapter("setChipType")
fun Chip.setChipType(type: Int){
    when(type){
        Constants.TYPE_EVENT_PRIVATE ->  {
            chipBackgroundColor = Converters.convertColorToColorStateList(context.resources.getColor(
                R.color.blue_800,context.theme))
            text = "Privado"
        }
        Constants.TYPE_EVENT_PUBLIC ->  {
            chipBackgroundColor = Converters.convertColorToColorStateList(context.resources.getColor(
                R.color.green_500,context.theme))
            text = "Publico"
        }
    }

}