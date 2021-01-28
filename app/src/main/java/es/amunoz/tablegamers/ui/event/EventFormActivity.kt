package es.amunoz.tablegamers.ui.event

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.util.Util
import com.google.firebase.Timestamp
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.databinding.ActivityEventFormBinding
import es.amunoz.tablegamers.databinding.ActivityFormAdBinding
import es.amunoz.tablegamers.models.Ad
import es.amunoz.tablegamers.models.Event
import es.amunoz.tablegamers.utils.*
import es.amunoz.tablegamers.viewmodels.AdViewModel
import es.amunoz.tablegamers.viewmodels.EventViewModel
import java.util.*

class EventFormActivity : AppCompatActivity(), StructViewData {
    private lateinit var binding: ActivityEventFormBinding
    private lateinit var viewModel: EventViewModel
    private lateinit var myEvt: Event
    private var dateEvent: Timestamp = Timestamp.now()
    private var isSelectedDate = false
    private var cont = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }
    override fun initViewModel() {
        viewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        initBinding()
    }

    override fun initBinding() {
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_event_form)


    }
    private fun saveEvt(){

        val date_create = Timestamp.now()
        val titleVal = binding.tietFevtTitle.text.toString()
        val maxPeopleVal = binding.tietFevtPlazas.text.toString()
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
        if (ValidatorUtil.isEmpty(descriptionVal)   ){
            validateField.add("Tipo")
            validate = false
        }
        if (!isSelectedDate   ){
            validateField.add("Fecha")
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

//
//            var ad = Ad().apply {
//                id = idVal
//                create_for = currentUser
//                description = descriptionVal
//                poblation = poblationVal
//                price = Integer.valueOf(priceVal)
//                province = provinceVal
//                state = stateVal
//                title = titleVal
//
//
//            }
//            saveAd(ad)
        }
    }
    fun clickSaveFormEvt(view: View) {}
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

   
}