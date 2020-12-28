package es.amunoz.tablegamers.ui.ad

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import es.amunoz.tablegamers.NavigationMenuActivity
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.adapters.AdsAdapter
import es.amunoz.tablegamers.databinding.FragmentAdBinding
import es.amunoz.tablegamers.utils.Constants
import es.amunoz.tablegamers.utils.StructViewData
import es.amunoz.tablegamers.viewmodels.AdViewModel
import es.amunoz.tablegamers.viewmodels.UserViewModel



class AdFragment : Fragment(), StructViewData {

    private lateinit var viewModel: AdViewModel
    private lateinit var myInflater: LayoutInflater
    private lateinit var myContainer: ViewGroup
    private lateinit var binding: FragmentAdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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


        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdFragment().apply {

            }
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AdViewModel::class.java)

        viewModel.callAds()

        val adsAdapter = AdsAdapter()
        binding.rvAds.adapter = adsAdapter
        viewModel.listAds.observe(viewLifecycleOwner,{
            it?.let {
                adsAdapter.submitList(it)
                Log.i("cambios","cambios")
            }

        })




    }

    override fun initBinding() {
        binding = DataBindingUtil.inflate(
            myInflater, R.layout.fragment_ad, myContainer, false
        )
    }
}