package es.amunoz.tablegamers.ui.message

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.adapters.AdsAdapter
import es.amunoz.tablegamers.adapters.AdsListener
import es.amunoz.tablegamers.adapters.UserMenssageListener
import es.amunoz.tablegamers.adapters.UserMessageAdapter
import es.amunoz.tablegamers.databinding.FragmentMessagesBinding
import es.amunoz.tablegamers.ui.ad.AdDetailActivity
import es.amunoz.tablegamers.ui.ad.FormAdActivity
import es.amunoz.tablegamers.utils.*
import es.amunoz.tablegamers.viewmodels.AdViewModel
import es.amunoz.tablegamers.viewmodels.MessageViewModel


class MessagesFragment : Fragment(), StructViewData  {

    private lateinit var viewModel: MessageViewModel
    private lateinit var viewModelAd: AdViewModel
    private lateinit var myInflater: LayoutInflater
    private lateinit var myContainer: ViewGroup
    private lateinit var binding: FragmentMessagesBinding
    private lateinit var userMessageAdapter: UserMessageAdapter
    private lateinit var adAdapter: AdsAdapter
    private lateinit var idAdSelected: String
    private lateinit var userCreateAd: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myInflater = inflater
        if (container != null) {
            myContainer = container
        }

        initBinding()
        initViewModel()

        return binding.root
    }

    companion object {

        fun newInstance() =
            MessagesFragment().apply {

            }
    }

    override fun initViewModel() {

        viewModelAd = ViewModelProvider(this).get(AdViewModel::class.java)
        viewModelAd.callAdChat()
       // viewModelAd.callAdsUser(FirebaseAuth.getInstance().uid.toString())
        viewModelAd.listAds.observe(viewLifecycleOwner, {
            it?.let {
                adAdapter.submitList(it)
            }
        })


    }

    override fun initBinding() {
        binding = DataBindingUtil.inflate(
            myInflater, R.layout.fragment_messages, myContainer, false
        )
        adAdapter =  AdsAdapter(AdsListener { ad ->
            idAdSelected = ad.id
            userCreateAd = ad.create_for
            loadUserChat()
        })
        binding.rvUsermessage.adapter = adAdapter

    }

    private fun loadUserChat(){
        viewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
        viewModel.callUserMessage(idAdSelected)
        binding.progressBar.visibility = View.VISIBLE
        viewModel.listUserMessage.observe(viewLifecycleOwner, {
            it?.let {
                if (it.isEmpty()) {
                    var dialog = MessageDialog(requireContext(), TypeMessage.INFO, "No tienes mensajes para este anuncio")
                    dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                        override fun onClickOKButton() {

                            initBinding()
                            initViewModel()

                        }
                    })
                    binding.progressBar.visibility = View.GONE
                } else {
                    userMessageAdapter.submitList(it)
                    binding.progressBar.visibility = View.GONE
                }

            }
        })

        userMessageAdapter = UserMessageAdapter(UserMenssageListener { user ->
            var intent = Intent(context, ChatActivity::class.java)
            intent.putExtra(Constants.EXTRA_ID_AD, idAdSelected)
            intent.putExtra(Constants.EXTRA_ID_USER, user.id)
            intent.putExtra(Constants.EXTRA_ID_USER_CREATE, userCreateAd)
            requireContext().startActivity(intent)

        })

        binding.rvUsermessage.adapter = userMessageAdapter


    }
}