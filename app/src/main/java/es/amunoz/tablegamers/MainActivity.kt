package es.amunoz.tablegamers

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import es.amunoz.tablegamers.utils.Constants

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        Handler().postDelayed({
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }, Constants.SPLASH_SCREEN_TIME)
    }
}