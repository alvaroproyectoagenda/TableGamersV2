 package es.amunoz.tablegamers.ui.invitation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.adapters.EventAdapter
import es.amunoz.tablegamers.adapters.EventListener
import es.amunoz.tablegamers.databinding.FragmentEventBinding
import es.amunoz.tablegamers.databinding.FragmentInvitationBinding
import es.amunoz.tablegamers.ui.event.EventDetailActivity
import es.amunoz.tablegamers.ui.event.EventFormActivity
import es.amunoz.tablegamers.utils.*
import es.amunoz.tablegamers.viewmodels.EventViewModel
import es.amunoz.tablegamers.viewmodels.InvitationViewModel

 class InvitationFragment : Fragment(), StructViewData {
    private lateinit var viewModel: InvitationViewModel
    private lateinit var myInflater: LayoutInflater
    private lateinit var myContainer: ViewGroup
    private lateinit var binding: FragmentInvitationBinding
    private lateinit var evtAdapter: EventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
        fun newInstance( ) =
            InvitationFragment().apply {
                arguments = Bundle().apply { 

                }
            }
    }


     override fun initViewModel() {

        viewModel = ViewModelProvider(this).get(InvitationViewModel::class.java)
        viewModel.callMyInvitations()
        viewModel.listEvents.observe(viewLifecycleOwner, {
            it?.let {
                if (it.isEmpty()) {
                    evtAdapter.submitList(it)
                    binding.tvInvitationsEmpty.visibility = View.VISIBLE
                } else {
                    evtAdapter.submitList(it)

                    binding.tvInvitationsEmpty.visibility = View.GONE
                }
            }
        })
         viewModel.isUpdateInvitation.observe(viewLifecycleOwner,{
             var typeMessage = TypeMessage.SUCCESS
             var message = "Invitaciones actualizadas"
             if(!it){
                 typeMessage = TypeMessage.ERROR
                 message = "Error al actualizar la invitación"
             }
             val dialog = MessageDialog(requireContext(), typeMessage, message)
             dialog.setOnClickListenerOKButton(object : OnClickListenerMessageDialog {
                 override fun onClickOKButton() {
                     viewModel.callMyInvitations()
                 }
             })

         })

     }

     override fun initBinding() {
         binding = DataBindingUtil.inflate(
             myInflater, R.layout.fragment_invitation, myContainer, false
         )
         evtAdapter = EventAdapter(EventListener {
             val dialog = ConfirmDialogInvitation(requireContext(), "Confirma si asistirás o no al evento")
             dialog.setOnClickListenerOKButton(object : OnClickListenerConfirmDialogInvitation {
                 override fun onClickOKButton() {
                    viewModel.updateEventAceptInvitation(it)
                 }

                 override fun onClickNOButton() {
                     viewModel.updateEventRetryInvitation(it)
                 }
             })
         })
         evtAdapter.notifyDataSetChanged()
         binding.rvInvintations.adapter = evtAdapter


     }






 }