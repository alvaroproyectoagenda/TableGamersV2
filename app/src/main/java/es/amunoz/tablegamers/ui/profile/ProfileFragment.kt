package es.amunoz.tablegamers.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.databinding.FragmentProfileBinding
import es.amunoz.tablegamers.models.User
import es.amunoz.tablegamers.utils.*
import es.amunoz.tablegamers.viewmodels.UserViewModel


// TODO: Rename parameter arguments, choose names that match



class ProfileFragment : Fragment(), StructViewData {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var myInflater: LayoutInflater
    private lateinit var myContainer: ViewGroup
    private lateinit var myUser: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myInflater = inflater
        if (container != null) {
            myContainer = container
        }

        initViewModel()
        initBinding()

        return binding.root

    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {

            }
    }


    override fun initViewModel() {
        //Init View model
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.getUser(FirebaseAuth.getInstance().currentUser?.uid.toString())
        viewModel.user.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {

                binding.tvProfileEmail.text = user.email
                binding.tietProfName.setText(user.name)
                binding.tietProfPhone.setText(user.phone)
                binding.tietProfNickname.setText(user.nickname)

                if (user.avatar != null) {
                    MethodUtil.loadAvatarImage(
                        user.avatar!!,
                        requireContext(),
                        binding.profileAvatar
                    )
                }

                myUser = user
            }
        })
        viewModel.emailForgotSend.observe(viewLifecycleOwner, Observer { emailForgotSend ->
            if (emailForgotSend) {
                val dialog = MessageDialog(
                    requireContext(),
                    TypeMessage.SUCCESS,
                    "Email enviado, revisa tu bandeja de entrada"
                )
                dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                    override fun onClickOKButton() {

                    }
                })
            } else {
                val dialog = MessageDialog(
                    requireContext(),
                    TypeMessage.WARNING,
                    "No se ha podido reestrablecer la password, revisa la direccion de email"
                )
                dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                    override fun onClickOKButton() {

                    }
                })
            }

        })
        //Obervers ViewModel
        viewModel.existNickname.observe(viewLifecycleOwner, Observer { existNickname ->
            if (existNickname) {

                var dialog = MessageDialog(
                    requireContext(), TypeMessage.WARNING,
                    "El nick ya está en uso"
                )
                dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                    override fun onClickOKButton() {
                        binding.nickname = ""

                    }
                })
            }
        })

        viewModel.isAddUser.observe(viewLifecycleOwner, Observer { isAddUser ->
            if (isAddUser) {

                var dialog = MessageDialog(
                    requireContext(),
                    TypeMessage.SUCCESS,
                    "¡Modificado con éxito!"
                )
                dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                    override fun onClickOKButton() {
                        //Go to main
                    }
                })

            } else {
                var dialog = MessageDialog(
                    requireContext(),
                    TypeMessage.WARNING,
                    viewModel.messageExceptionRegisterUser.value.toString()
                )
                dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                    override fun onClickOKButton() {

                    }
                })
            }


        })
    }

    override fun initBinding() {

        binding = DataBindingUtil.inflate(
            myInflater, R.layout.fragment_profile, myContainer, false
        )
        binding.btnProfPassword.setOnClickListener(View.OnClickListener {
            clickRestartPassword()
        })
        binding.btnProfSave.setOnClickListener(View.OnClickListener {
            clickSaveChanges()
        })
        binding.tietProfNickname.setOnFocusChangeListener { _, hasFocus ->

            if(!hasFocus){
                changeActionNickname()
            }
        }
    }
    private fun changeActionNickname(){
        val nickname = binding.tietProfNickname.text.toString()
        if (!ValidatorUtil.isEmpty(nickname)){
            viewModel.checkNickname(nickname)
        }
    }
    private fun clickRestartPassword(){
       /* val email = binding.tvProfileEmail.text.toString()
        viewModel.fogotPassword(email)*/


    }
    private fun clickSaveChanges(){

        val nameVal =binding.tietProfName.text.toString()
        val nicknameVal =binding.tietProfNickname.text.toString()
        val phoneVal =binding.tietProfPhone.text.toString()

        val validateField: MutableList<String> = mutableListOf()
        var validate = true
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

        if(validate){

            var userToUpdate = User().apply {
                id = myUser.id
                name = nameVal
                email = myUser.email
                nickname = nicknameVal
                phone = phoneVal
                avatar = myUser.avatar

            }
             viewModel.add(userToUpdate)
        }else{
            val info = validateField.joinToString("\n")
            val dialog = MessageDialog(
                requireContext(),
                TypeMessage.INFO,
                "Revisa los campos:\n" + info
            )
            dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                override fun onClickOKButton() {
                    validateField.removeAll(validateField)
                    validate = true
                }
            })
        }

    }
}