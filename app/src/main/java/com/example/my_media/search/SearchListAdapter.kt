package com.example.my_media.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.my_media.databinding.SearchRecyclerviewItemBinding
import com.example.my_media.mypage.MyVideoModel

class SearchListAdapter(val itemClickListener: (SearchModel) -> Unit) : ListAdapter<SearchModel, SearchListAdapter.ViewHolder>(
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
        val binding = SearchRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}