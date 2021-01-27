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
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.databinding.ItemAdBinding
import es.amunoz.tablegamers.databinding.ItemChatMeBinding
import es.amunoz.tablegamers.databinding.ItemChatOtherBinding
import es.amunoz.tablegamers.models.Ad
import es.amunoz.tablegamers.models.Message
import es.amunoz.tablegamers.utils.Constants
import es.amunoz.tablegamers.utils.MethodUtil
/*
class MessageAdapter(val clickListener: MessageListener): ListAdapter<Message, MessageAdapter.ViewHolder>(MessagesDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolderMe private constructor(val binding: ItemChatMeBinding) : RecyclerView.ViewHolder(binding.root){


        fun bind(item: Message, clickListener: MessageListener) {
           binding.message = item
           binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolderMe {
                val layoutInflater = LayoutInflater.from(parent.context)
                var  binding = ItemChatMeBinding.inflate(layoutInflater, parent, false)
                return ViewHolderMe(binding)
            }
        }
    }
    class ViewHolderOther private constructor(val binding: ItemChatOtherBinding) : RecyclerView.ViewHolder(binding.root){


        fun bind(item: Message, clickListener: MessageListener) {
            binding.message = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolderOther {
                val layoutInflater = LayoutInflater.from(parent.context)
                var  binding = ItemChatOtherBinding.inflate(layoutInflater, parent, false)
                return ViewHolderOther(binding)
            }
        }
    }
}

class MessagesDiffCallback : DiffUtil.ItemCallback<Message>() {

    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }


}

class MessageListener(val clickListener: (item: Message) -> Unit) {
    fun onClick(item: Message) = clickListener(item)
}

*/
