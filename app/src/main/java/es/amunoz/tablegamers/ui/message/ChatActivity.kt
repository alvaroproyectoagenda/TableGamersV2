package es.amunoz.tablegamers.ui.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.adapters.ChatAdapter
import es.amunoz.tablegamers.databinding.ActivityAdDetailBinding
import es.amunoz.tablegamers.databinding.ActivityChatBinding
import es.amunoz.tablegamers.models.Message
import es.amunoz.tablegamers.utils.Constants
import es.amunoz.tablegamers.utils.StructViewData
import es.amunoz.tablegamers.viewmodels.AdViewModel
import es.amunoz.tablegamers.viewmodels.MessageViewModel

class ChatActivity : AppCompatActivity(), StructViewData {


    private lateinit var binding: ActivityChatBinding
    private lateinit var viewModel: MessageViewModel
    private lateinit var idAd: String
    private lateinit var idUser: String
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
            viewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
            viewModel.callChat(idAd,idUser)
            viewModel.listChat.observe(this, {
                adapterChat = ChatAdapter(it as MutableList<Message>)
                binding.rvChat.adapter = adapterChat
            })


        }
    }

    override fun initBinding() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_chat)

        binding.ibChatSend.setOnClickListener {
           if(!TextUtils.isEmpty( binding.etMsg.text)){
               Toast.makeText(this, binding.etMsg.text, Toast.LENGTH_SHORT).show()
           }
        }

    }
}