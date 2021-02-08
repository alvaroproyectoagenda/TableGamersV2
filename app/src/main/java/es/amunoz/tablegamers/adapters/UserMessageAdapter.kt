package es.amunoz.tablegamers.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.amunoz.tablegamers.databinding.ItemUserMessagesBinding
import es.amunoz.tablegamers.models.User

class UserMessageAdapter(val clickListener: UserMenssageListener): ListAdapter<User, UserMessageAdapter.ViewHolder>(UserMenssageDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemUserMessagesBinding) : RecyclerView.ViewHolder(binding.root){


        fun bind(item: User, clickListener: UserMenssageListener) {


            binding.user = item
            binding.clickListener = clickListener
            binding.executePendingBindings()


        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemUserMessagesBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class UserMenssageDiffCallback : DiffUtil.ItemCallback<User>() {

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }


}

class UserMenssageListener(val clickListener: (item: User) -> Unit) {
    fun onClick(item: User) = clickListener(item)
}