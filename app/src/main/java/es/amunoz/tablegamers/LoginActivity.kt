package es.amunoz.tablegamers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import es.amunoz.tablegamers.utils.MethodUtil
import es.amunoz.tablegamers.utils.TypeMessage

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun clickGoRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun clickLogin(view: View){
        MethodUtil.showDialogMessage(TypeMessage.WARNING,this, "Usuario Registrado")
    }
}