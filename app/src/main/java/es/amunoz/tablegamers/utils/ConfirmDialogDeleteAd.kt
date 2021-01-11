package es.amunoz.tablegamers.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import es.amunoz.tablegamers.R

/**
 * Objeto Dialogo de mensaje
 *
 * Clase que crea un Dialog customizado y captura el evento de click
 *
 * @param myContext Recibe el contexto de la activity que lo va a lanzar
 * @param[type] Enum de tipo de mensaje que indica si será de aviso, alerta, info o error
 * @param[context] contexto de la aplicación que muestra el mensaje
 * @param[message] mensaje de text que se mostrará en la alerta
 * @property dialog Objeto Dialog
 * @property buttonOK Objeto Button asociado al aceptar
 * @property listener Objeto del listener que escucha los botones o elementos del dialog
 *
 */
class ConfirmDialogDeleteAd(private var myContext: Context,   var message: String): View.OnClickListener  {

    private var dialog: Dialog = Dialog(myContext)
    private lateinit var buttonOK: Button
    private lateinit var buttonNO: Button
    private lateinit var listener: OnClickListenerConfirmDialogDeleteAd
    init {
        loadDialog()
    }

    public fun dimissDialog(){
        dialog.dismiss()
    }
    fun loadDialog(){
        //Configuramos el Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_confirm_action)
        dialog.setCancelable(false)

        (dialog.findViewById<View>(R.id.titleDialogAvatar) as TextView).text = message

        //Cargamos todos los elementos y mostramos
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        buttonOK = (dialog.findViewById<View>(R.id.btn_confirm_si) as Button)
        buttonOK.setOnClickListener(this)
        buttonNO = (dialog.findViewById<View>(R.id.btn_confirm_no) as Button)
        buttonNO.setOnClickListener(this)
        dialog.show()
        dialog.window!!.attributes = lp
    }

    fun setOnClickListenerOKButton(listener: OnClickListenerConfirmDialogDeleteAd) {
        this.listener = listener
    }
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id){
                R.id.btn_confirm_si -> {
                    listener.onClickOKButton()
                    dimissDialog()
                }
                R.id.btn_confirm_no -> {
                    dimissDialog()
                }
            }
        }


    }


}

interface OnClickListenerConfirmDialogDeleteAd {
    fun onClickOKButton()
}
