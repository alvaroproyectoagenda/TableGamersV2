package es.amunoz.tablegamers.utils

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import es.amunoz.tablegamers.R

data class SendMessageDialog(private var myContext: Context): View.OnClickListener  {

    private var dialog: Dialog = Dialog(myContext)
    private lateinit var buttonSend: Button
    public lateinit var editText: EditText
    private lateinit var listener: OnClickListenerSendMessageDialog
    init {
        loadDialog()
    }

    public fun dimissDialog(){
        dialog.dismiss()
    }
    fun loadDialog(){
        //Configuramos el Dialog
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_send_message)
        dialog.setCancelable(false)

        //Cargamos todos los elementos y mostramos
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        buttonSend = (dialog.findViewById<View>(R.id.btnSendMsg) as Button)
        editText = (dialog.findViewById<View>(R.id.et_msg) as EditText)
        buttonSend.setOnClickListener(this)
        dialog.show()
        dialog.window!!.attributes = lp
    }

    fun setOnClickListenerSendButton(listener: OnClickListenerSendMessageDialog) {
        this.listener = listener
    }
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id){
                R.id.btnSendMsg -> {
                    listener.onClickSendButton()
                    dimissDialog()
                }
            }
        }


    }


}

interface OnClickListenerSendMessageDialog {
    fun onClickSendButton()
}
