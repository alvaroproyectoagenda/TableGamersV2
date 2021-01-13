package es.amunoz.tablegamers.utils

import android.app.Dialog
import android.content.Context
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
class MessageDialog(private var myContext: Context, var type: TypeMessage, var message: String): View.OnClickListener  {

    private var dialog: Dialog = Dialog(myContext)
    private lateinit var buttonOK: Button
    private lateinit var listener: OnClickListenerMessageDialog
    init {
        loadDialog()
    }

    public fun dimissDialog(){
        dialog.dismiss()
    }
    fun loadDialog(){
        //Configuramos el Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_info)
        dialog.setCancelable(false)
        when(type){
            TypeMessage.ERROR -> {
                (dialog.findViewById<View>(R.id.iconMessage) as ImageView).setImageResource(R.drawable.ic_error)
                (dialog.findViewById<View>(R.id.dialogHeader) as LinearLayout).setBackgroundColor(myContext.resources.getColor(
                    R.color.red_500))
                (dialog.findViewById<View>(R.id.btnClose) as Button).setBackgroundColor(myContext.resources.getColor(
                    R.color.red_500))
            }
            TypeMessage.WARNING -> {
                (dialog.findViewById<View>(R.id.iconMessage) as ImageView).setImageResource(R.drawable.ic_warning)
                (dialog.findViewById<View>(R.id.dialogHeader) as LinearLayout).setBackgroundColor(myContext.resources.getColor(
                    R.color.amber_700))
                (dialog.findViewById<View>(R.id.btnClose) as Button).setBackgroundColor(myContext.resources.getColor(
                    R.color.amber_700))
            }
            TypeMessage.INFO -> {
                (dialog.findViewById<View>(R.id.iconMessage) as ImageView).setImageResource(R.drawable.ic_info)
                (dialog.findViewById<View>(R.id.dialogHeader) as LinearLayout).setBackgroundColor(myContext.resources.getColor(
                    R.color.blue_500))
                (dialog.findViewById<View>(R.id.btnClose) as Button).setBackgroundColor(myContext.resources.getColor(
                    R.color.blue_500))
            }
            TypeMessage.SUCCESS -> {
                (dialog.findViewById<View>(R.id.iconMessage) as ImageView).setImageResource(R.drawable.ic_success)
                (dialog.findViewById<View>(R.id.dialogHeader) as LinearLayout).setBackgroundColor(myContext.resources.getColor(
                    R.color.green_500))
                (dialog.findViewById<View>(R.id.btnClose) as Button).setBackgroundColor(myContext.resources.getColor(
                    R.color.green_500))
            }
        }
        //Mostramos el mensaje
        (dialog.findViewById<View>(R.id.tvTitleFilter) as TextView).text = message

        //Cargamos todos los elementos y mostramos
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        buttonOK = (dialog.findViewById<View>(R.id.btnClose) as Button)
        buttonOK.setOnClickListener(this)
        dialog.show()
        dialog.window!!.attributes = lp
    }

    fun setOnClickListenerOKButton(listener: OnClickListenerMessageDialog) {
        this.listener = listener
    }
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id){
                R.id.btnClose -> {
                    listener.onClickOKButton()
                    dimissDialog()
                }
            }
        }


    }


}

interface OnClickListenerMessageDialog {
    fun onClickOKButton()
}
