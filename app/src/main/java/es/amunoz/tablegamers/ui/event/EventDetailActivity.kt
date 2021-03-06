package es.amunoz.tablegamers.ui.event

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.databinding.ActivityAdDetailBinding
import es.amunoz.tablegamers.databinding.ActivityEventDetailBinding
import es.amunoz.tablegamers.models.Event
import es.amunoz.tablegamers.models.User
import es.amunoz.tablegamers.utils.*
import es.amunoz.tablegamers.viewmodels.AdViewModel
import es.amunoz.tablegamers.viewmodels.EventViewModel
import es.amunoz.tablegamers.viewmodels.UserViewModel

class EventDetailActivity : AppCompatActivity(), StructViewData {

    private lateinit var binding: ActivityEventDetailBinding
    private lateinit var viewModel: EventViewModel
    private lateinit var viewModelUser: UserViewModel
    private lateinit var idEvt: String
    private lateinit var myEvt: Event
    private lateinit var userCreate: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)
        initViewModel()
    }


    override fun initViewModel() {
        val bundle = intent.extras
        if(bundle?.getString(Constants.EXTRA_ID_EVT) != null) {
            idEvt = bundle.getString(Constants.EXTRA_ID_EVT)!!
            viewModel = ViewModelProvider(this).get(EventViewModel::class.java)
            viewModelUser = ViewModelProvider(this).get(UserViewModel::class.java)
            viewModel.callEvent(idEvt)
            viewModel.event.observe(this, {

                myEvt = it
                initBinding()

            })
            viewModel.isUpdateUserEvent.observe(this,{
                if(it){


                    val dialog = MessageDialog(this, TypeMessage.SUCCESS, "¡Listo! Te has apuntado al evento")
                    dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                        override fun onClickOKButton() {
                            binding.btnGoEvent.text = "Ya vas a asistir a este evento"
                            binding.btnGoEvent.isEnabled = false
                            finish()
                        }
                    })
                }else{
                    val dialog = MessageDialog(this, TypeMessage.ERROR, "¡UPS! Ocurrió un error al apuntarte al evento")
                    dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                        override fun onClickOKButton() {

                        }
                    })
                }
            })

        }

    }
    private fun loadUserInfo(){
        viewModelUser.getUser(myEvt.create_by)
        viewModelUser.user.observe(this,{
            binding.ivDetailsEvtAvatar.setImageDrawable(MethodUtil.getDrawableAvatar(it.avatar!!,this))
            binding.tvDetailsEvtUsercreate.text = it.name
        })
    }

    override fun initBinding() {

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_event_detail)
        binding.event = myEvt
        loadUserInfo()
        if(myEvt.create_by == viewModel.getCurrentUserUID()){
            binding.btnGoEvent.text = "Este evento lo creaste tú"
            binding.btnGoEvent.isEnabled = false
        }else{
            if(myEvt.users_confirm.isNotEmpty() && myEvt.users_confirm.size == myEvt.max_people){
                binding.btnGoEvent.text = "Plazas agotadas"
                binding.btnGoEvent.isEnabled = false
            }else{
                if(myEvt.users_confirm.isNotEmpty() && myEvt.users_confirm.contains(viewModel.getCurrentUserUID())){
                    binding.btnGoEvent.text =  "Ya vas a asistir a este evento"
                    binding.btnGoEvent.isEnabled = false
                }else{
                    binding.btnGoEvent.text = "¡Voy a ir!"
                    binding.btnGoEvent.isEnabled = true
                }
            }

        }
    }

    fun clickGoToEvent(view: View) {
        val usersEvent = mutableListOf<String>()
        if(myEvt.users.isNotEmpty()){
             usersEvent.addAll(myEvt.users)
        }
        usersEvent.add(viewModel.getCurrentUserUID())
        myEvt.users = usersEvent
        myEvt.users_confirm = usersEvent
        viewModel.userGoToEvent(myEvt)



    }

    fun clickBack(view: View) {
        finish()
    }
}