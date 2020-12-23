package es.amunoz.tablegamers

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import es.amunoz.tablegamers.utils.Constants

class SplashScreenActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Obtenemos el usuarios actual de firebaseAuth
        val user = FirebaseAuth.getInstance().currentUser
        //Si existe vamos al Main
        if(user!=null){

            Handler().postDelayed({
                startActivity(Intent(this,NavigationMenuActivity::class.java))//Cambar por main
                finish()
            }, Constants.SPLASH_SCREEN_TIME)
        //Si no existe vamos al Login
        }else{
            Handler().postDelayed({
                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }, Constants.SPLASH_SCREEN_TIME)
        }

    }
}