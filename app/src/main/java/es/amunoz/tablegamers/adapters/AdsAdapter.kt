package es.amunoz.tablegamers.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.amunoz.tablegamers.R
import es.amunoz.tablegamers.databinding.ItemAdBinding
import es.amunoz.tablegamers.models.Ad
import es.amunoz.tablegamers.utils.MethodUtil

class AdsAdapter(val clickListener: AdsListener): ListAdapter<Ad, AdsAdapter.ViewHolder>(AdsDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ItemAdBinding) : RecyclerView.ViewHolder(binding.root){


        fun bind(item: Ad, clickListener: AdsListener) {

           binding.itemTvTitle.text = item.title
           binding.ad = item
            binding.clickListener = clickListener
           binding.executePendingBindings()


        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAdBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class AdsDiffCallback : DiffUtil.ItemCallback<Ad>() {

    override fun areItemsTheSame(oldItem: Ad, newItem: Ad): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: Ad, newItem: Ad): Boolean {
        return oldItem == newItem
    }


}

class AdsListener(val clickListener: (item: Ad) -> Unit) {
    fun onClick(item: Ad) = clickListener(item)
}