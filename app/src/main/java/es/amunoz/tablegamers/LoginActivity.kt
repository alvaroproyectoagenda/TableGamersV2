package es.amunoz.tablegamers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import es.amunoz.tablegamers.utils.MessageDialog
import es.amunoz.tablegamers.utils.MethodUtil
import es.amunoz.tablegamers.utils.OnClickListenerMessageDialog
import es.amunoz.tablegamers.utils.TypeMessage

class LoginActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun clickGoRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun clickLogin(view: View) {

        var dialog = MessageDialog(this,TypeMessage.WARNING, "Usuario Registrado")
        dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
            override fun onClickOKButton() {
                Toast.makeText(applicationContext, "FALSE", Toast.LENGTH_SHORT).show()
            }
        })
    }




}