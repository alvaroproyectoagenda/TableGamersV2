package es.amunoz.tablegamers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import es.amunoz.tablegamers.databinding.ActivityLoginBinding
import es.amunoz.tablegamers.utils.*
import es.amunoz.tablegamers.viewmodels.UserViewModel

class LoginActivity : AppCompatActivity(), StructViewData{

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initBinding()
        initViewModel()
    }

    fun clickGoRegister(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun clickLogin(view: View) {
    val email  = binding.tietLogEmail.text.toString()
    val pass  = binding.tietLogPass.text.toString()

        if(ValidatorUtil.isEmpty(email) || ValidatorUtil.isEmpty(pass)){
            val dialog = MessageDialog(this,TypeMessage.INFO, "Debes de rellenar el email y/o password")
            dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                override fun onClickOKButton() {

                }
            })
        }else{
            viewModel.loginUser(email, pass)
        }
    }

    override fun initViewModel() {
        //Init View model
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.firebaseUser.observe(this, Observer { firebaseUser ->
            if (firebaseUser != null) {
                startActivity(Intent(this,NavigationMenuActivity::class.java))//Cambar por main
                finish()
            }else{
                val dialog = MessageDialog(this,TypeMessage.WARNING, "No se ha podido acceder.\nRevisa tu correo y/o contraseña")
                dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                    override fun onClickOKButton() {

                    }
                })
            }
        })
        viewModel.emailForgotSend.observe(this, Observer { emailForgotSend ->
            if(emailForgotSend){
                val dialog = MessageDialog(this,TypeMessage.SUCCESS, "Email enviado, revisa tu bandeja de entrada")
                dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                    override fun onClickOKButton() {

                    }
                })
            }else{
                val dialog = MessageDialog(this,TypeMessage.WARNING, "No se ha podido reestrablecer la password, revisa la direccion de email")
                dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                    override fun onClickOKButton() {

                    }
                })
            }

        })
    }

    override fun initBinding() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        //Init
    }

    fun clickForgotPassword(view: View) {

        val email = binding.tietLogEmail.text.toString()
        if(ValidatorUtil.isEmail(email)){
            viewModel.fogotPassword(email)
        }else{
            val dialog = MessageDialog(this,TypeMessage.INFO, "Revisa el campo email")
            dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                override fun onClickOKButton() {

                }
            })
        }
    }


}