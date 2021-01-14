package es.amunoz.tablegamers.ui.message

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.databinding.FragmentMessagesBinding
import es.amunoz.tablegamers.utils.StructViewData
import es.amunoz.tablegamers.viewmodels.AdViewModel
import es.amunoz.tablegamers.viewmodels.MessageViewModel


class MessagesFragment : Fragment(), StructViewData  {

    private lateinit var viewModel: MessageViewModel
    private lateinit var myInflater: LayoutInflater
    private lateinit var myContainer: ViewGroup
    private lateinit var binding: FragmentMessagesBinding


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
    }

    override fun initBinding() {
        binding = DataBindingUtil.inflate(
            myInflater, R.layout.fragment_messages, myContainer, false
        )
    }
}