package es.amunoz.tablegamers.adapters

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.balysv.materialripple.MaterialRippleLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.util.Util
import com.google.firebase.storage.FirebaseStorage
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.utils.MethodUtil
import kotlinx.coroutines.coroutineScope

class ImageSliderAdapter(private val activity: Activity, var images: List<String>): PagerAdapter() {
    override fun getCount(): Int {
       return images.size
    }


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
       return view is RelativeLayout
    }

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
    }
    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = inflater.inflate(R.layout.item_image_slider, container, false)
        //val avatar: String = images[position]
        val avatar: String = getItems(position)
        //val image = v.findViewById<View>(R.id.image) as ImageView
        val image = v.findViewWithTag<View>("imgSlider") as ImageView
        Log.i("uri", "${position-1}")



       MethodUtil.loadImageFromStorage(avatar, activity, image)



        (container as ViewPager).addView(v)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as RelativeLayout?)
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    fun setItems(items: List<String>){
        images = items
        notifyDataSetChanged()
    }

    fun getItems(pos: Int): String {return images[pos] }
}