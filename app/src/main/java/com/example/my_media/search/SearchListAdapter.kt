package com.example.my_media.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.my_media.R
import com.example.my_media.databinding.ItemSearchBinding

class SearchListAdapter(val context: Context, val itemClickListener: (SearchModel) -> Unit) : ListAdapter<SearchModel, SearchListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<SearchModel>() {
        override fun areItemsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean {
            return oldItem.searchedTitle == newItem.searchedTitle
        }

        override fun areContentsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean {
            return oldItem == newItem
        }
    }
) {
    inner class ViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                itemClickListener(getItem(position))
            }
        }
        fun bind(item: SearchModel) = with(binding) {
            searchImgItem.load(item.searchedVideo)
            searchTxtItem.text = item.searchedTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lastPosition = -1
        if (holder.adapterPosition > lastPosition){
            val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_row)
            holder.bind(getItem(position))
            holder.itemView.startAnimation(animation)
        }
    }
}