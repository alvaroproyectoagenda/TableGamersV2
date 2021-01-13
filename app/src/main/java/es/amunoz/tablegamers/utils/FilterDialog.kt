package es.amunoz.tablegamers.utils

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import es.amunoz.tablegamers.R
import org.w3c.dom.Text

class FilterDialog(private var myContext: Context): View.OnClickListener  {

    private var dialog: Dialog = Dialog(myContext)
    private lateinit var buttonFilter: Button
    private lateinit var buttonClear: Button
    private lateinit var validationFilterText: TextView
    private lateinit var titleFilter: EditText
    private lateinit var priceSinceFilter: EditText
    private lateinit var priceUntilFilter: EditText
    private lateinit var provinceFilter: Spinner
    private lateinit var listener: OnClickListenerFilterDialog

    var isFilter: Boolean = true
    lateinit var filter: Filter

    init {
        loadDialog()
    }

    public fun dimissDialog(){
        dialog.dismiss()
    }
    fun loadDialog(){
        //Configuramos el Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_ad_filter)
        dialog.setCancelable(true)


        //Cargamos todos los elementos y mostramos
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        titleFilter = (dialog.findViewById<View>(R.id.et_filter_title) as EditText)
        validationFilterText = (dialog.findViewById<View>(R.id.tv_filter_validation) as TextView)
        priceSinceFilter = (dialog.findViewById<View>(R.id.et_filter_price_1) as EditText)
        priceUntilFilter = (dialog.findViewById<View>(R.id.et_filter_price_2) as EditText)
        provinceFilter = (dialog.findViewById<View>(R.id.sp_filter_province) as Spinner)

        buttonFilter = (dialog.findViewById<View>(R.id.btn_filter) as Button)
        buttonClear = (dialog.findViewById<View>(R.id.btn_filter_clear) as Button)
        buttonFilter.setOnClickListener(this)
        buttonClear.setOnClickListener(this)

        dialog.show()
        dialog.window!!.attributes = lp
    }

    fun setOnClickListenerOKButton(listener: OnClickListenerFilterDialog) {
        this.listener = listener
    }
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id){
                R.id.btn_filter -> {


                    var priceSince = if (priceSinceFilter.text.isEmpty()) 0 else priceSinceFilter.text.toString().toInt()
                    var princeUntil = if (priceUntilFilter.text.isEmpty()) 0 else priceUntilFilter.text.toString().toInt()

                    filter = Filter().apply {
                        title = titleFilter.text.toString()
                        price_since = priceSince
                        price_until = princeUntil
                        province = provinceFilter.selectedItem.toString()
                    }

                    if(filter.validationPrice()){
                        isFilter = true
                        listener.onClickFilterButton()
                        dimissDialog()
                    }else{
                        validationFilterText.visibility = View.VISIBLE
                        validationFilterText.text = "Revisa el filtro de precios"
                    }

                }
                R.id.btn_filter_clear -> {
                    isFilter = false
                    listener.onClickClearFilterButton()
                    dimissDialog()
                }
            }
        }


    }


}

interface OnClickListenerFilterDialog {
    fun onClickFilterButton()
    fun onClickClearFilterButton()
}
