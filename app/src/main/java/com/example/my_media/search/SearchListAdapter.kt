package com.example.my_media.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.my_media.databinding.SearchRecyclerviewItemBinding

class SearchListAdapter : ListAdapter<SearchModel, SearchListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<SearchModel>() {
        override fun areItemsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean {
            return oldItem.searchedTitle == newItem.searchedTitle
        }

        override fun areContentsTheSame(oldItem: SearchModel, newItem: SearchModel): Boolean {
            return oldItem == newItem
        }
    }
) {
    inner class ViewHolder(private val binding: SearchRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchModel) = with(binding) {
            searchImgItem.load(item.searchedVideo)
            searchTxtItem.text = item.searchedTitle

            root.setOnClickListener {
                itemClick?.onClick(adapterPosition)
            }
        }
    }

    interface ItemClick {
        fun onClick(position: Int)
    }

    var itemClick: ItemClick? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SearchRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            itemClick?.onClick(position)
        }
    }
}