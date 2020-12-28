package es.amunoz.tablegamers.ui.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.databinding.ActivityAvatarBinding
import es.amunoz.tablegamers.utils.MessageDialog
import es.amunoz.tablegamers.utils.OnClickListenerMessageDialog
import es.amunoz.tablegamers.utils.StructViewData
import es.amunoz.tablegamers.utils.TypeMessage
import es.amunoz.tablegamers.viewmodels.UserViewModel

class AvatarActivity : AppCompatActivity(), StructViewData {

    private lateinit var binding: ActivityAvatarBinding
    private lateinit var viewModel: UserViewModel
    private var imageName = String()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViewModel()
    }

    override fun initViewModel() {
        //Init View model
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.isAvatarUpdate.observe(this,  { isAvatarUpdate ->
            if (isAvatarUpdate) {

                val dialog = MessageDialog(this, TypeMessage.SUCCESS, "¡Avatar modificado con éxito!")
                dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                    override fun onClickOKButton() {
                       finish()
                    }
                })

            } else {
                var dialog = MessageDialog(this, TypeMessage.WARNING, viewModel.messageException.value.toString())
                dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                    override fun onClickOKButton() {

                    }
                })
            }


        })

    }

    override fun initBinding() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_avatar)
    }

    fun clickAvatarSelected(view: View){
        imageName = view.tag.toString()
        binding.btnDialogAvatar.isEnabled = true

    }

    fun clickSaveAvatar(view: View) {

        if(FirebaseAuth.getInstance().currentUser!=null){
            viewModel.updateAvatarUser(FirebaseAuth.getInstance().currentUser?.uid.toString(), imageName)
        }
    }
}