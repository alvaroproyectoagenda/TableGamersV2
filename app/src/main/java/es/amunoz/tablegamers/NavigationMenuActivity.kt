package es.amunoz.tablegamers

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import es.amunoz.tablegamers.models.User
import es.amunoz.tablegamers.utils.MethodUtil
import es.amunoz.tablegamers.utils.StructViewData
import es.amunoz.tablegamers.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.nav_header_main.view.*

class NavigationMenuActivity : AppCompatActivity(){

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var viewModel: UserViewModel
    private lateinit var textViewNameUser: TextView
    private lateinit var textViewEmailUser: TextView
    private lateinit var imageViewAvatarUser: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_menu)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        initViewModel()
        initComponent()
        initNavigation()

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun initComponent(){
        drawerLayout = findViewById(R.id.drawer_layout)
        navView  = findViewById(R.id.nav_view)

        val view: View = navView.getHeaderView(0)
        textViewNameUser  = view.findViewById(R.id.nav_header_name)
        textViewEmailUser  = view.findViewById(R.id.nav_header_email)
        imageViewAvatarUser  = view.findViewById(R.id.nav_header_image)


    }
    private fun initNavigation(){
        val navController = findNavController(R.id.nav_host_fragment)
        
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_ad, R.id.nav_myad, R.id.nav_event, R.id.nav_invitation, R.id.nav_profile, R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        viewModel.getUser(FirebaseAuth.getInstance().currentUser?.uid.toString())
        viewModel.user.observe(this,  { user ->
          if(user!=null){
              textViewNameUser.text = user.name
              textViewEmailUser.text = user.email
              user.avatar?.let {    MethodUtil.loadAvatarImage(it, this ,imageViewAvatarUser) }
          }
      })
    }




}