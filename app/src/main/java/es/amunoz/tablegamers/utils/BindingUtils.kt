package es.amunoz.tablegamers.utils

import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.databinding.BindingAdapter
import androidx.databinding.adapters.Converters
import com.google.android.material.chip.Chip
import es.amunoz.tablegamers.models.Ad

@BindingAdapter("setHeaderItemImagen")
fun ImageView.setHeaderItemImage(item: Ad){
    MethodUtil.loadImageFromStorage(item.images[0], context, this)
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
