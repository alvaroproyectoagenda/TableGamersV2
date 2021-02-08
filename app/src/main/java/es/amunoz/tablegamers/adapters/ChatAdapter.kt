package es.amunoz.tablegamers.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.databinding.ItemAdBinding
import es.amunoz.tablegamers.databinding.ItemChatMeBinding
import es.amunoz.tablegamers.databinding.ItemChatOtherBinding
import es.amunoz.tablegamers.models.Ad
import es.amunoz.tablegamers.models.Message
import es.amunoz.tablegamers.utils.Constants
import es.amunoz.tablegamers.utils.MethodUtil

class ChatAdapter(var data: MutableList<Message>) : RecyclerView.Adapter<ChatAdapter.MessageViewHolder<*>>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder<*> {
        val context = parent.context
        return when (viewType) {
            1 -> {
                val view = LayoutInflater.from(context).inflate(R.layout.item_chat_me, parent, false)
                MeChatViewHolder(view)
            }
            2 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_other, parent, false)
                OtherChatViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder<*>, position: Int) {
        val item = data[position]

        when (holder) {
            is MeChatViewHolder -> holder.bind(item)
            is OtherChatViewHolder -> holder.bind(item)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount(): Int = data.size

    override fun getItemViewType(position: Int): Int {
        var user = FirebaseAuth.getInstance().currentUser
        if (user!=null){
            return if(user.uid == data[position].user){
                1
            }else{
                2
            }
        }
        return 0
    }

    class MeChatViewHolder(val view: View) : MessageViewHolder<Message>(view) {
        private val itemMessage = view.findViewById<TextView>(R.id.item_chat_text)

        override fun bind(item: Message) {

            itemMessage.text = item.message
        }
    }
    class OtherChatViewHolder(val view: View) : MessageViewHolder<Message>(view) {
        private val itemMessage = view.findViewById<TextView>(R.id.item_chat_text)


        override fun bind(item: Message) {
           itemMessage.text = item.message

        }


    }
    abstract class MessageViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }
}