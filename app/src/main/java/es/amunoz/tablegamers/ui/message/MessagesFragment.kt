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
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.adapters.AdsAdapter
import es.amunoz.tablegamers.adapters.AdsListener
import es.amunoz.tablegamers.adapters.UserMenssageListener
import es.amunoz.tablegamers.adapters.UserMessageAdapter
import es.amunoz.tablegamers.databinding.FragmentMessagesBinding
import es.amunoz.tablegamers.ui.ad.AdDetailActivity
import es.amunoz.tablegamers.ui.ad.FormAdActivity
import es.amunoz.tablegamers.utils.Constants
import es.amunoz.tablegamers.utils.StructViewData
import es.amunoz.tablegamers.viewmodels.AdViewModel
import es.amunoz.tablegamers.viewmodels.MessageViewModel


class MessagesFragment : Fragment(), StructViewData  {

    private lateinit var viewModel: MessageViewModel
    private lateinit var myInflater: LayoutInflater
    private lateinit var myContainer: ViewGroup
    private lateinit var binding: FragmentMessagesBinding
    private lateinit var userMessageAdapter: UserMessageAdapter


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
        viewModel = ViewModelProvider(this).get(MessageViewModel::class.java)
        viewModel.callUserMessage("294e813a-eccf-48cb-a783-73ef668052a1")
        viewModel.listUserMessage.observe(viewLifecycleOwner, {
            it?.let {
                userMessageAdapter.submitList(it)

            }
        })
    }

    override fun initBinding() {
        binding = DataBindingUtil.inflate(
            myInflater, R.layout.fragment_messages, myContainer, false
        )
        userMessageAdapter = UserMessageAdapter(UserMenssageListener { user ->
        /*    var intent = Intent(context, AdDetailActivity::class.java)

            intent.putExtra(Constants.EXTRA_ID_AD, ad.id)
            requireContext().startActivity(intent)*/

        })
        binding.rvUsermessage.adapter = userMessageAdapter
    }
}