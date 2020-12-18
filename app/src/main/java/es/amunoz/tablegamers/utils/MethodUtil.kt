package es.amunoz.tablegamers.utils

import android.app.Activity
import android.app.Dialog
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
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
        /**
         * Metodo que muestra un Dialog de alerta customizado
         * @return Muestra el dialog
         * @param[type] Enum de tipo de mensaje que indica si será de aviso, alerta, info o error
         * @param[context] contexto de la aplicación que muestra el mensaje
         * @param[message] mensaje de text que se mostrará en la alerta
         */
        fun showDialogMessage(type: TypeMessage, context: Activity, message: String){
            //Configuramos el Dialog
            var dialog: Dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_info)
            dialog.setCancelable(true)
            //Dibujamos la cabecera del color que corresponda el type de mensaje
            when(type){
                TypeMessage.ERROR -> {
                    (dialog.findViewById<View>(R.id.iconMessage) as ImageView).setImageResource(R.drawable.ic_error)
                    (dialog.findViewById<View>(R.id.dialogHeader) as LinearLayout).setBackgroundColor(context.resources.getColor(R.color.red_500))
                    (dialog.findViewById<View>(R.id.btnClose) as Button).setBackgroundColor(context.resources.getColor(R.color.red_500))
                }
                 TypeMessage.WARNING -> {
                     (dialog.findViewById<View>(R.id.iconMessage) as ImageView).setImageResource(R.drawable.ic_warning)
                     (dialog.findViewById<View>(R.id.dialogHeader) as LinearLayout).setBackgroundColor(context.resources.getColor(R.color.amber_700))

                     (dialog.findViewById<View>(R.id.btnClose) as Button).setBackgroundColor(context.resources.getColor(R.color.amber_700))
                 }
                TypeMessage.INFO -> {
                    (dialog.findViewById<View>(R.id.iconMessage) as ImageView).setImageResource(R.drawable.ic_info)
                    (dialog.findViewById<View>(R.id.dialogHeader) as LinearLayout).setBackgroundColor(context.resources.getColor(R.color.blue_500))
                    (dialog.findViewById<View>(R.id.btnClose) as Button).setBackgroundColor(context.resources.getColor(R.color.blue_500))

                }
                TypeMessage.SUCCESS -> {
                    (dialog.findViewById<View>(R.id.iconMessage) as ImageView).setImageResource(R.drawable.ic_success)
                    (dialog.findViewById<View>(R.id.dialogHeader) as LinearLayout).setBackgroundColor(context.resources.getColor(R.color.green_500))
                    (dialog.findViewById<View>(R.id.btnClose) as Button).setBackgroundColor(context.resources.getColor(R.color.green_500))
                }
            }
            //Mostramos el mensaje
            (dialog.findViewById<View>(R.id.titleMessage) as TextView).text = message

            //Cargamos todos los elementos y mostramos
            val lp = WindowManager.LayoutParams()
            lp.copyFrom(dialog.window!!.attributes)
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            (dialog.findViewById<View>(R.id.btnClose) as Button).setOnClickListener { v ->
                dialog.dismiss()
            }
            dialog.show()
            dialog.window!!.attributes = lp
        }

    }
}