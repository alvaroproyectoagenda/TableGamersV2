package es.amunoz.tablegamers.ui.ad

import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.databinding.ActivityFormAdBinding
import es.amunoz.tablegamers.models.Ad
import es.amunoz.tablegamers.utils.*
import es.amunoz.tablegamers.viewmodels.AdViewModel

class FormAdActivity : AppCompatActivity(), StructViewData {

    private lateinit var binding: ActivityFormAdBinding
    private lateinit var viewModel: AdViewModel
    private var storageReference = FirebaseStorage.getInstance().reference
    private var isCreate = true
    private var currentUser = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private var listOfPath = arrayListOf<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViewModel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Constants.PICK_IMAGE_REQUEST && resultCode ==  RESULT_OK && data!=null){
            generateFilePath(data)
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(AdViewModel::class.java)
        viewModel.isAddAd.observe(this, {
            if (it) {

                var dialog = MessageDialog(this, TypeMessage.SUCCESS, "¡Anuncio creado con éxito!")
                dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                    override fun onClickOKButton() {
                        finish()
                    }
                })

            } else {
                var dialog = MessageDialog(
                    this,
                    TypeMessage.WARNING,
                    viewModel.messageException.value.toString()
                )
                dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                    override fun onClickOKButton() {

                    }
                })
            }
        })
    }

    override fun initBinding() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_form_ad)

        if(!isCreate){
            binding.btnFadDelete.visibility = View.GONE
            binding.btnFadForm.text = "Modificar"
        }
    }

  private fun showFileChooserImage(){
      var intent = Intent()
      intent.type = "image/*"
      intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
      intent.action = Intent.ACTION_GET_CONTENT
      startActivityForResult(
          Intent.createChooser(intent, "Selecciona hasta 3 imagenes"),
          Constants.PICK_IMAGE_REQUEST
      )
  }

    private fun createNewAd(){
        var idVal = MethodUtil.generateID()
        var titleVal = binding.tietFadTitle.text.toString()
        var priceVal = binding.tietFadPrice.text.toString()
        var stateVal = "Nuevo"
        var poblationVal = binding.spFadPoblation.selectedItem.toString()
        var provinceVal =  binding.spFadProvince.selectedItem.toString()
        var descriptionVal = binding.tietFadDescription.text.toString()


        val validateField: MutableList<String> = mutableListOf()
        var validate = true
        if (ValidatorUtil.isEmpty(titleVal)   ){
            validateField.add("Titulo")
            validate = false
        }
        if (ValidatorUtil.isEmpty(priceVal) && !ValidatorUtil.isNumber(priceVal)   ){
            validateField.add("Precio")
            validate = false
        }
        if (ValidatorUtil.isEmpty(stateVal)   ){
            validateField.add("Estado")
            validate = false
        }
        if (ValidatorUtil.isEmpty(poblationVal) || ValidatorUtil.isEmpty(provinceVal)   ){
            validateField.add("Población y/o Provincia")
            validate = false
        }
        if (ValidatorUtil.isEmpty(descriptionVal) ){
            validateField.add("Descripcion")
            validate = false
        }

        if(!validate){
            val info = validateField.joinToString("\n")
            val dialog = MessageDialog(this, TypeMessage.INFO, "Revisa los campos:\n" + info)
            dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                override fun onClickOKButton() {
                    validateField.removeAll(validateField)
                    validate = true
                }
            })
        } else {


            var ad = Ad().apply {
                id = idVal
                  create_for = currentUser
                  description = descriptionVal
                  poblation = poblationVal
                  price = Integer.valueOf(priceVal)
                  province = provinceVal
                  state = stateVal
                  title = titleVal


            }
           saveAd(ad)
        }
    }

    private fun generateFilePath(intent: Intent){

        if(intent.clipData !=null){
            val clipData: ClipData = intent.clipData!!
            val count = clipData.itemCount
            showPreviewImage(clipData.getItemAt(0).uri)
            for (i in 0 until count){
                val uri = clipData.getItemAt(i).uri
                listOfPath.add(uri)
            }
        }else if(intent.data != null){
            listOfPath.add(intent.data!!)
            showPreviewImage(intent.data!!)
        }

    }

    private fun showPreviewImage(uri: Uri){
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        binding.ivFadHedar.setImageBitmap(bitmap)
        binding.ivFadHedar.adjustViewBounds = true
    }

    private fun saveAd(ad: Ad){
        if(listOfPath.isNotEmpty()){

            val listImagesFirestore = arrayListOf<String>()
            val progressDialog = ProgressBar(this)
            progressDialog.progress = 10
            progressDialog.isShown
            val idAd = ad.id
            listOfPath.forEachIndexed { index, it ->
                 val nameFile = idAd.plus("_".plus(index))
                listImagesFirestore.add(Constants.GS_FIRESTORE_AD_PATH.plus("$idAd/$nameFile"))
                 storageReference.child("ads/$idAd/$nameFile").putFile(it)
                     .addOnSuccessListener {

                 }.addOnFailureListener{

                 }.addOnProgressListener {

                         val pro: Double =
                             100.0 * it.bytesTransferred / it.totalByteCount
                         progressDialog.progress = pro.toInt()

                     }

            }
            ad.images = listImagesFirestore
            viewModel.saveAd(ad)

        }else{
            val dialog = MessageDialog(this, TypeMessage.INFO, "Revisa los campos:\nDebes de incluir al menos una imagen")
            dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                override fun onClickOKButton() {

                }
            })
        }
    }
    fun clickUploadImages(view: View) {
        showFileChooserImage()
    }

    fun clickSaveFormAd(view: View) {
        createNewAd()
    }
}