package es.amunoz.tablegamers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    override fun initComponent() {
        TODO("Not yet implemented")
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun initBinding() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.btnAdd.setOnClickListener {
            clickActionRegister()
        }
        binding.terms = false
    }

    /**
     * Metodo que inserta el objeto en Firestore y Auth
     */
    private fun clickActionRegister(){
        var id = MethodUtil.generateID()
        val name = binding.tietName.text.toString()
        val email = binding.tietEmail.text.toString()
        val nickname = binding.tietNickname.text.toString()
        val phone = binding.tietPhone.text.toString()
        val pass = binding.tietPassword.text.toString()
        val passRepeat = binding.tietRepeatPassword.text.toString()


        val validateField: MutableList<String> = mutableListOf()
        var validate = true
        if (!ValidatorUtil.isEmail(email)   ){
            validateField.add("Email")
            validate = false
        }
        if (ValidatorUtil.isEmpty(name)   ){
            validateField.add("Nombre")
            validate = false
        }
        if (ValidatorUtil.isEmpty(nickname)   ){
            validateField.add("Nickname")
            validate = false
        }
        if (!ValidatorUtil.isPhone(phone)   ){
            validateField.add("Telefono")
            validate = false
        }
        if (!ValidatorUtil.areEqualPassword(pass, passRepeat)   ){
            validateField.add("Passwords")
            validate = false
        }



        if(!validate){
            val info = validateField.joinToString("\n")
            val dialog = MessageDialog(this,TypeMessage.INFO, "Debes de rellenar\n"+info)
            dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                override fun onClickOKButton() {
                    validateField.removeAll(validateField)
                    validate = true
                }
            })
        } else {
            /* var user = User().apply {
             id = MethodUtil.generateID()
             name = binding.tietName.text.toString()
             email = binding.tietEmail.text.toString()
             nickname = binding.tietEmail.text.toString()
             phone = binding.tietPhone.text.toString()

         }
         viewModel.add(user)
         viewModel.isAddUser.observe(this, Observer { isAddUser ->
             if (isAddUser) {
                 var dialog = MessageDialog(this,TypeMessage.SUCCESS, "¡Registrado con éxito!")
                 dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                     override fun onClickOKButton() {

                     }
                 })

             } else {
                 var dialog = MessageDialog(this,TypeMessage.ERROR, "¡Ups! Ocurrio un error al registrar")
                 dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                     override fun onClickOKButton() {

                     }
                 })
             }


         })*/

        }


    }
}