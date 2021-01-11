package es.amunoz.tablegamers.ui.ad

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import es.amunoz.tablegamers.NavigationMenuActivity
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.adapters.AdsAdapter
import es.amunoz.tablegamers.adapters.AdsListener
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
    private lateinit var adsAdapter: AdsAdapter
    private var isMyAd: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val arg = arguments?.getBoolean("argIdUser")
        Log.i("Args",arg.toString())
        isMyAd = arg!=null

        myInflater = inflater
        if (container != null) {
            myContainer = container
        }

        initBinding()
        initViewModel()

        return binding.root

    }

    override fun onStart() {
        super.onStart()
        initViewModel()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            AdFragment().apply {

            }
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AdViewModel::class.java)
        if(isMyAd){
            viewModel.callAdsUser(FirebaseAuth.getInstance().uid.toString())
        }else{
            viewModel.callAds()
        }
        viewModel.listAds.observe(viewLifecycleOwner, {
            it?.let {
                adsAdapter.submitList(it)
                Log.i("cambios", "cambios")
            }
        })
    }

    override fun initBinding() {
        binding = DataBindingUtil.inflate(
            myInflater, R.layout.fragment_ad, myContainer, false
        )
        adsAdapter = AdsAdapter(AdsListener { ad ->
            var intent = Intent(context, AdDetailActivity::class.java)
            if(isMyAd){
                intent = Intent(context, FormAdActivity::class.java)
            }
            intent.putExtra(Constants.EXTRA_ID_AD, ad.id)
            requireContext().startActivity(intent)

        })
        binding.rvAds.adapter = adsAdapter
        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(context, FormAdActivity::class.java))
        }
    }

}