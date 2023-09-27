package com.example.my_media.home.subscribe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.my_media.databinding.ItemSubscribeBinding

class HomeSubscribeListAdapter: ListAdapter<HomeSubscribeModel, HomeSubscribeListAdapter.ViewHolder>(
    object: DiffUtil.ItemCallback<HomeSubscribeModel>() {
        override fun areItemsTheSame(oldItem: HomeSubscribeModel, newItem: HomeSubscribeModel): Boolean {
            return oldItem.imgThumbnail == newItem.imgThumbnail
        }

        override fun areContentsTheSame(oldItem: HomeSubscribeModel, newItem: HomeSubscribeModel): Boolean {
            return oldItem == newItem
        }
    }
) {
    inner class ViewHolder(private val binding: ItemSubscribeBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeSubscribeModel) = with(binding) {
            imgThumbnail.load(item.imgThumbnail)
            txtName.text = item.txtName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSubscribeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}