package es.amunoz.tablegamers.ui.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.Timestamp
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.adapters.ChatAdapter
import es.amunoz.tablegamers.databinding.ActivityAdDetailBinding
import es.amunoz.tablegamers.databinding.ActivityChatBinding
import es.amunoz.tablegamers.models.Message
import es.amunoz.tablegamers.utils.Constants
import es.amunoz.tablegamers.utils.MethodUtil
import es.amunoz.tablegamers.utils.StructViewData
import es.amunoz.tablegamers.viewmodels.AdViewModel
import es.amunoz.tablegamers.viewmodels.MessageViewModel

class ChatActivity : AppCompatActivity(), StructViewData {


    private lateinit var binding: ActivityChatBinding
    private lateinit var viewModel: MessageViewModel
    private lateinit var idAd: String
    private lateinit var idUser: String
    private lateinit var idUserCreateAd: String
    private lateinit var adapterChat: ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initBinding()
        initViewModel()

    }

    override fun initViewModel() {
        val bundle = intent.extras
        if(bundle?.getString(Constants.EXTRA_ID_AD) != null) {
            idAd = bundle.getString(Constants.EXTRA_ID_AD)!!
            idUser = bundle.getString(Constants.EXTRA_ID_USER)!!
            idUserCreateAd = bundle.getString(Constants.EXTRA_ID_USER_CREATE)!!
            viewModel = ViewModelProvider(this).get(MessageViewModel::class.java)

            if(idUser!=idUserCreateAd){
                viewModel.callChat(idAd,idUser)
            }else{
                var myUser = viewModel.getUser()
                viewModel.callChat(idAd,myUser)
            }

            viewModel.listChat.observe(this, {


                adapterChat = ChatAdapter(it as MutableList<Message>)
                adapterChat.notifyDataSetChanged()
                binding.rvChat.adapter = adapterChat
            })
            viewModel.isResponseMessage.observe(this,{
                if(it){
                    if(idUser!=idUserCreateAd){
                        viewModel.callChat(idAd,idUser)
                    }else{
                        var myUser = viewModel.getUser()
                        viewModel.callChat(idAd,myUser)
                    }
                    binding.etMsg.text.clear()
                }
            })


        }
    }

    override fun initBinding() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_chat)

        binding.ibSendChat.setOnClickListener {
           if(!TextUtils.isEmpty( binding.etMsg.text)){


               var msg = Message().apply {
                   id = MethodUtil.generateID()
                   message = binding.etMsg.text.toString()
                   user = viewModel.getUser()
               }

               var userCreateChat =  idUser
               if(idUser==idUserCreateAd){
                   userCreateChat = viewModel.getUser()
               }

             viewModel.responseMessage(msg,idAd,userCreateChat)
           }
        }

    }
}