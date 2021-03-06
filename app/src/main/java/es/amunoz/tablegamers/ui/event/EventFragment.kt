package es.amunoz.tablegamers .ui.event

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.adapters.EventAdapter
import es.amunoz.tablegamers.adapters.EventListener
import es.amunoz.tablegamers.databinding.FragmentEventBinding
import es.amunoz.tablegamers.utils.*
import es.amunoz.tablegamers.viewmodels.EventViewModel
import kotlinx.android.synthetic.main.app_bar_main.*


class EventFragment : Fragment(), StructViewData {
    private lateinit var viewModel: EventViewModel
    private lateinit var myInflater: LayoutInflater
    private lateinit var myContainer: ViewGroup
    private lateinit var binding: FragmentEventBinding
    private lateinit var evtAdapter: EventAdapter
    private lateinit var typeEvent: TypeFilterEvent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }
    override fun onStart() {
        super.onStart()
         if(binding.tvEventTitle.text == "Voy a ir"){
             typeEvent = TypeFilterEvent.GOTOEVENTS
             viewModel.callGoToEvents()
         }else{
            initViewModel()
         }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

     /*   val arg = arguments?.getBoolean("argIdUser")
        Log.i("Args",arg.toString())
        isMyAd = arg!=null
*/
        myInflater = inflater
        if (container != null) {
            myContainer = container
        }

        initBinding()
        initViewModel()

        return binding.root

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            EventFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


    override fun initViewModel() {
        setHasOptionsMenu(true);
        typeEvent = TypeFilterEvent.PUBLICEVENTS
        viewModel = ViewModelProvider(this).get(EventViewModel::class.java)
        viewModel.callPublicEvent()
        viewModel.listEvents.observe(viewLifecycleOwner, {
            it?.let {
                evtAdapter.submitList(it)
                evtAdapter.notifyDataSetChanged()
                Log.i("cambios", "cambios")
            }
        })
        viewModel.isDeleteEvt.observe(viewLifecycleOwner, {
            var typeMessage = TypeMessage.SUCCESS
            var message = "Evento eliminado"
            if (!it) {
                typeMessage = TypeMessage.ERROR
                message = "Error al eliminar evento"
            }
            val dialog = MessageDialog(requireContext(), typeMessage, message)
            dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                override fun onClickOKButton() {
                    viewModel.callMyEvents()
                }
            })
        })
    }

    override fun initBinding() {
        binding = DataBindingUtil.inflate(
            myInflater, R.layout.fragment_event, myContainer, false
        )
        binding.tvEventTitle.text = "Eventos"
        evtAdapter = EventAdapter(EventListener {
            when (typeEvent) {
                TypeFilterEvent.MYEVENTS -> {
                    val dialog = ConfirmDialogDeleteAd(
                        requireContext(),
                        "Estas seguro/a de eliminar este evento?"
                    )
                    dialog.setOnClickListenerOKButton(object :
                        OnClickListenerConfirmDialogDeleteAd {
                        override fun onClickOKButton() {
                            viewModel.deleteEvent(it.id)

                        }
                    })
                }
                TypeFilterEvent.GOTOEVENTS -> {
                    val intent = Intent(context, EventDetailActivity::class.java)
                    intent.putExtra(Constants.EXTRA_ID_EVT, it.id)
                    requireContext().startActivity(intent)

                }
                TypeFilterEvent.PUBLICEVENTS -> {

                    val intent = Intent(context, EventDetailActivity::class.java)
                    intent.putExtra(Constants.EXTRA_ID_EVT, it.id)
                    requireContext().startActivity(intent)
                }
            }

        })
        binding.rvEvt.adapter = evtAdapter
        binding.floatingActionButton.setOnClickListener {
            startActivity(Intent(context, EventFormActivity::class.java))
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        activity?.menuInflater?.inflate(R.menu.menu_events, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.item_event_public -> {
                binding.tvEventTitle.text = "Eventos"
                typeEvent = TypeFilterEvent.PUBLICEVENTS
                viewModel.callPublicEvent()

            }
            R.id.item_event_go -> {
                binding.tvEventTitle.text = "Voy a ir"
                typeEvent = TypeFilterEvent.GOTOEVENTS
                viewModel.callGoToEvents()

            }
            R.id.item_event_myevents -> {
                binding.tvEventTitle.text = "Mis eventos"
                typeEvent = TypeFilterEvent.MYEVENTS
                viewModel.callMyEvents()

            }
        }
        return super.onOptionsItemSelected(item)
    }

}