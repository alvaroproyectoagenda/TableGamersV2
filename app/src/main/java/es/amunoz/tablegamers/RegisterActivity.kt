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
import es.amunoz.tablegamers.databinding.ActivityRegisterBinding
import es.amunoz.tablegamers.models.User
import es.amunoz.tablegamers.utils.*
import es.amunoz.tablegamers.viewmodels.UserViewModel

class RegisterActivity : AppCompatActivity(), StructViewData {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initBinding()
        initViewModel()

    }
    override fun initViewModel() {
        //Init View model
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        //Obervers ViewModel
        viewModel.existNickname.observe(this, Observer { existNickname ->
            if(existNickname){

                var dialog = MessageDialog(this,TypeMessage.WARNING,
                    "El nick {${binding.nickname}} ya está en uso"
                )
                dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                    override fun onClickOKButton() {
                        binding.nickname = ""

                    }
                })
            }
        })
        viewModel.isAddUser.observe(this, Observer { isAddUser ->
            if (isAddUser) {

                var dialog = MessageDialog(this,TypeMessage.SUCCESS, "¡Registrado con éxito!")
                dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                    override fun onClickOKButton() {

                        startActivity(Intent(applicationContext,SplashScreenActivity::class.java))
                        finish()
                    }
                })

            } else {
                var dialog = MessageDialog(this,TypeMessage.WARNING, viewModel.messageExceptionRegisterUser.value.toString())
                dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                    override fun onClickOKButton() {

                    }
                })
            }


        })
    }
    override fun initBinding() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_register)
        //Init
        binding.terms = false
        //Events
        binding.tietNickname.setOnFocusChangeListener { _, hasFocus ->

            if(!hasFocus){
              changeActionNickname()
            }
        }
    }


    private fun clickActionRegister(){
        val idVal = MethodUtil.generateID()
        val nameVal = binding.tietName.text.toString()
        val emailVal = binding.tietEmail.text.toString()
        val nicknameVal = binding.tietNickname.text.toString()
        val phoneVal = binding.tietPhone.text.toString()
        val passVal = binding.tietPassword.text.toString()
        val passRepeatVal = binding.tietRepeatPassword.text.toString()


        val validateField: MutableList<String> = mutableListOf()
        var validate = true
        if (!ValidatorUtil.isEmail(emailVal)   ){
            validateField.add("Email")
            validate = false
        }
        if (ValidatorUtil.isEmpty(nameVal)   ){
            validateField.add("Nombre")
            validate = false
        }
        if (ValidatorUtil.isEmpty(nicknameVal)   ){
            validateField.add("Nickname")
            validate = false
        }
        if (!ValidatorUtil.isPhone(phoneVal)   ){
            validateField.add("Telefono")
            validate = false
        }
        if (!ValidatorUtil.areEqualPassword(passVal, passRepeatVal)   ){
            validateField.add("Passwords")
            validate = false
        }

        if(!validate){
            val info = validateField.joinToString("\n")
            val dialog = MessageDialog(this,TypeMessage.INFO, "Revisa los campos:\n"+info)
            dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                override fun onClickOKButton() {
                    validateField.removeAll(validateField)
                    validate = true
                }
            })
        } else {
            var user = User().apply {
             id = idVal
             name = nameVal
             email = emailVal
             avatar = Constants.PATH_STORAGE_DEFAULT_AVATAR
             nickname = nicknameVal
             phone = phoneVal

         }
         viewModel.registerUserAuth(emailVal, passVal, user)

        }


    }
    private fun changeActionNickname(){
        val nickname = binding.tietNickname.text.toString()
        if (!ValidatorUtil.isEmpty(nickname)){
            viewModel.checkNickname(nickname)
        }
    }

    fun clickRegister(view: View) {
       clickActionRegister()
    }
}