package es.amunoz.tablegamers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import es.amunoz.tablegamers.databinding.ActivityRegisterBinding
import es.amunoz.tablegamers.models.User
import es.amunoz.tablegamers.utils.MethodUtil
import es.amunoz.tablegamers.utils.StructViewData
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
            var user = User().apply {
                id = MethodUtil.generateID()
                name = binding.tietName.text.toString()
                email = binding.tietEmail.text.toString()
                nickname = binding.tietEmail.text.toString()
                phone = binding.tietPhone.text.toString()

            }

            if (viewModel.add(user)) {
                Toast.makeText(applicationContext, "Insertado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, "No se pudo insertar", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }
}