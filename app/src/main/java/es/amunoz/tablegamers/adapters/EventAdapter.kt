package es.amunoz.tablegamers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.amunoz.tablegamers.databinding.ItemEventBinding
import es.amunoz.tablegamers.models.Event

class EventAdapter(val clickListener: EventListener): ListAdapter<Event, EventAdapter.ViewHolder>(EventDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root){


        fun bind(item: Event, clickListener: EventListener) {

            binding.event = item
            binding.clickListener = clickListener
            binding.executePendingBindings()


        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemEventBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class EventDiffCallback : DiffUtil.ItemCallback<Event>() {

    override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
        return oldItem == newItem
    }


}

class EventListener(val clickListener: (item: Event) -> Unit) {
    fun onClick(item: Event) = clickListener(item)
}