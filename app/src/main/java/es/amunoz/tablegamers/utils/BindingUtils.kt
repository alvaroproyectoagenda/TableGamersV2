package es.amunoz.tablegamers.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import es.amunoz.tablegamers.models.Ad

@BindingAdapter("setHeaderItemImagen")
fun ImageView.setHeaderItemImage(item: Ad){
    MethodUtil.loadImageFromStorage(item.images[0], context, this)
}
@BindingAdapter("setEuroSymbol")
fun TextView.setEuroSymbol(item: Ad){
    text =  item.price.toString().plus("€")
}