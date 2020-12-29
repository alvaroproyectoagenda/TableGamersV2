package es.amunoz.tablegamers.ui.ad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.utils.StructViewData

class AdDetailActivity : AppCompatActivity(), StructViewData {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_detail)
    }

    override fun initViewModel() {

    }

    override fun initBinding() {

    }
}