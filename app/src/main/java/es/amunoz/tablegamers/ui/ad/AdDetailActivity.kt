package es.amunoz.tablegamers.ui.ad

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewpager.widget.ViewPager
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.adapters.ImageSliderAdapter
import es.amunoz.tablegamers.databinding.ActivityAdDetailBinding
import es.amunoz.tablegamers.models.Ad
import es.amunoz.tablegamers.utils.*
import es.amunoz.tablegamers.viewmodels.AdViewModel


class AdDetailActivity : AppCompatActivity(), StructViewData {


    private lateinit var binding: ActivityAdDetailBinding
    private lateinit var viewModel: AdViewModel
    private lateinit var idAd: String
    private lateinit var myAd: Ad
    private  var listImagesStorage = listOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun initViewModel() {
        //Init View model
        val bundle = intent.extras
        if(bundle?.getString(Constants.EXTRA_ID_AD) != null) {
            idAd = bundle.getString(Constants.EXTRA_ID_AD)!!
            viewModel = ViewModelProvider(this).get(AdViewModel::class.java)
            viewModel.getAdByID(idAd)
            viewModel.myAd.observe(this, {

                myAd = it
                listImagesStorage = if(it.images.isEmpty()){
                    arrayListOf<String>(Constants.NOT_FOUND_IMAGE_AD)
                }else{
                    it.images
                }


                initBinding()
                initSliderImage()
            })


        }

    }

    override fun initBinding() {


        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_ad_detail)
        binding.ad = myAd


    }

     private fun initSliderImage(){
        var adapterImagesSlide = ImageSliderAdapter(this, listOf())
        adapterImagesSlide.setItems(listImagesStorage)

         binding.viewPagerImageSlider.adapter= adapterImagesSlide
         binding.viewPagerImageSlider.currentItem = 0
         initBottomDots(binding.layoutDots, adapterImagesSlide.count, 0)
         binding.viewPagerImageSlider.addOnPageChangeListener(object :
             ViewPager.OnPageChangeListener {

             override fun onPageScrollStateChanged(state: Int) {
             }

             override fun onPageScrolled(
                 position: Int,
                 positionOffset: Float,
                 positionOffsetPixels: Int
             ) {

             }

             override fun onPageSelected(position: Int) {
                 initBottomDots(binding.layoutDots, adapterImagesSlide.count, position)
             }

         })

    }
     private fun initBottomDots(linearLayout: LinearLayout, size: Int, current: Int){
        val dots = mutableListOf<ImageView>()
        linearLayout.removeAllViews()

        for (i in 0 until size) {
            dots.add(ImageView(this))
            val widthHeight = 15
            val params =
                LinearLayout.LayoutParams(ViewGroup.LayoutParams(widthHeight, widthHeight))
            params.setMargins(10, 10, 10, 10)
            dots[i].layoutParams = params
            dots[i].setImageResource(R.drawable.shape_circle)
            dots[i].setColorFilter(
                ContextCompat.getColor(this, R.color.overlay_dark_10),
                PorterDuff.Mode.SRC_ATOP
            )
            linearLayout.addView(dots[i])
        }

        if (dots.size > 0) {
            dots[current].setColorFilter(
                ContextCompat.getColor(this, R.color.colorPrimaryDark),
                PorterDuff.Mode.SRC_ATOP
            )
        }
    }
}