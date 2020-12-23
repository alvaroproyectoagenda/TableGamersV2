package es.amunoz.tablegamers.utils

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.SplashScreenActivity



class ConfirmDialog(): DialogFragment()   {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.dialog_confirm, container, false)
        v.findViewById<Button>(R.id.btn_confirm_no).setOnClickListener {
            this.dismiss()
        }
        v.findViewById<Button>(R.id.btn_confirm_si).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            this.dismiss()
            activity?.finish()
            val intent = Intent(requireContext(), SplashScreenActivity::class.java)
            requireContext().startActivity(intent)

        }
        v.findViewById<TextView>(R.id.titleMessageConfirm).text = "¿Estás seguro de cerrar sesion?"

        return v
    }



}