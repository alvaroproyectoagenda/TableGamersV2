package es.amunoz.tablegamers.ui.event

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.util.Util
import com.google.firebase.Timestamp
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.adapters.UserMessageAdapter
import es.amunoz.tablegamers.adapters.UsersSpinnerAdapter
import es.amunoz.tablegamers.databinding.ActivityEventFormBinding
import es.amunoz.tablegamers.databinding.ActivityFormAdBinding
import es.amunoz.tablegamers.models.*
import es.amunoz.tablegamers.utils.*
import es.amunoz.tablegamers.viewmodels.AdViewModel
import es.amunoz.tablegamers.viewmodels.EventViewModel
import es.amunoz.tablegamers.viewmodels.InvitationViewModel
import es.amunoz.tablegamers.viewmodels.UserViewModel
import java.util.*

class EventFormActivity : AppCompatActivity(), StructViewData {
    private lateinit var binding: ActivityEventFormBinding
    private lateinit var viewModel: EventViewModel
    private lateinit var viewModelUser: UserViewModel
    private lateinit var viewModelInvitation: InvitationViewModel
    private lateinit var myEvt: Event
    private var dateEvent: Timestamp = Timestamp.now()
    private var isSelectedDate = false
    private var isPrivate = false
    private lateinit var adapterSpinnerUser: UsersSpinnerAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }
    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        viewModelUser = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModelInvitation = ViewModelProvider(this).get(InvitationViewModel::class.java)
        initBinding()
        viewModel.isAddEvt.observe(this,{

            if(it){
                val dialog = MessageDialog(this, TypeMessage.SUCCESS, "Evento creado con éxito")
                dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                    override fun onClickOKButton() {
                        finish()
                    }
                })
            }else{
                val dialog = MessageDialog(this, TypeMessage.ERROR, "UPS! Ocurrió un error al guardar el evento")
                dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                    override fun onClickOKButton() {

                    }
                })
            }

        })


    }

    override fun initBinding() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_event_form)
        binding.spFevtState.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                 when(position){
                     1 -> {
                       binding.btnShowUser.visibility = View.VISIBLE
                         isPrivate = true
                    }
                    0 -> {
                        binding.btnShowUser.visibility = View.GONE
                        isPrivate = false
                    }
                }

            }

        }


    }
    private fun saveEvt(){


        val titleVal = binding.tietFevtTitle.text.toString()
        var maxPeopleVal = binding.tietFevtPlazas.text.toString()
        val typeVal = MethodUtil.getTypeEvent( binding.spFevtState.selectedItem.toString())
        val descriptionVal = binding.tietFevtDescription.text.toString()

        val validateField: MutableList<String> = mutableListOf()
        var validate = true
        if (ValidatorUtil.isEmpty(titleVal)   ){
            validateField.add("Titulo")
            validate = false
        }
        if (ValidatorUtil.isEmpty(maxPeopleVal) && !ValidatorUtil.isNumber(maxPeopleVal)   ){
            validateField.add("Plazas")
            validate = false
        }
        if (typeVal == -1 ){
            validateField.add("Tipo")
            validate = false
        }
        if (ValidatorUtil.isEmpty(descriptionVal) || descriptionVal.length> Constants.MAX_CHARSET_DESCRIPTIONS_EVENT  ){
            validateField.add("Descripcion ((Max ${Constants.MAX_CHARSET_DESCRIPTIONS_EVENT} caracteres\")")
            validate = false
        }
        if (!isSelectedDate   ){
            validateField.add("Fecha")
            validate = false
        }

        if(isPrivate) {
            val data = getListIDUsers()
            if(data.isEmpty()){
                validateField.add("Invitados")
                validate = false
            }
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
            val idVal = MethodUtil.generateID()
            val currentUser = viewModel.getCurrentUserUID()
            val dateCreateTmsp = Timestamp.now()
            var usersVal = listOf<String>()
            var usersConfirmVal =  listOf<String>()

            //NOTE: Solo rellenamos users no users_confirm, ese array se rellenará cuando acepteo o no la invitacion
            if(isPrivate){
                val data = getListIDUsers()
                usersVal = data
                maxPeopleVal = "${data.size}"
            }

            var evt = Event().apply {
                id = idVal
                title = titleVal
                create_by = currentUser
                date = dateEvent
                date_creation = dateCreateTmsp
                description = descriptionVal
                max_people = Integer.valueOf(maxPeopleVal)
                type = typeVal
                users = usersVal
                users_confirm = usersConfirmVal

            }

            if(isPrivate){
                usersVal.forEach {
                    viewModelInvitation.addInvitation(evt.id, it)
                }
                viewModel.addEvent(evt)

            }else{
                viewModel.addEvent(evt)
            }

        }
    }
    fun clickSaveFormEvt(view: View) {
       saveEvt()

    }
    private fun getListIDUsers(): List<String>{
        val arrayIds = mutableListOf<String>()
        adapterSpinnerUser.listUserSelected.forEach {
            it.id.let{ id ->
                if (id != null) {
                    arrayIds.add(id)
                }
            }
        }
        return arrayIds
    }
    fun clickSelectDateEvt(view: View) {

        val newFragment = DatePickerFragment.newInstance { _, year, month, day ->

            val mesStr: String = if (month + 1 in 1..9) {
                "0" + (month + 1)
            } else {
                (month + 1).toString() + ""
            }
            val diaStr: String = if (day in 1..9) {
                "0$day"
            } else {
                day.toString() + ""
            }

            val dateFormatSql = "${year}-$mesStr-$diaStr";
            val dateCalendar = MethodUtil.getCalendarByString(dateFormatSql)

            binding.tvDetailsEvtDay.text = diaStr
            binding.tvDetailsEvtMonth.text =  MethodUtil.getMonthName(dateCalendar)
            dateEvent = Timestamp(dateCalendar.time)
            isSelectedDate = true
        }

        newFragment.show(supportFragmentManager, "datePicker")
    }
    fun loadDialogList(list: List<User>){
        adapterSpinnerUser = UsersSpinnerAdapter(this, list)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Invitaciones")
            .setCancelable(true)
            .setAdapter(adapterSpinnerUser, DialogInterface.OnClickListener { dialog, which ->
               })
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                val tam = adapterSpinnerUser.listUserSelected.size
                if(adapterSpinnerUser.listUserSelected.size!=0){
                    binding.tietFevtPlazas.setText("$tam")
                }
            })

        dialog.create()
        dialog.show()

    }
    fun clickInvitations(view: View) {
           viewModelUser.callUsers()
           viewModelUser.users.observe(this,{
               loadDialogList(it)
        })
    }


}