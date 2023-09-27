package com.example.my_media.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.api.load
import com.bumptech.glide.Glide
import com.example.my_media.databinding.SearchRecyclerviewItemBinding

class SearchListAdapter (private val context1: Context) :
    RecyclerView.Adapter<SearchListAdapter.ItemViewHolder>() {
    var items = ArrayList<SearchModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchListAdapter.ItemViewHolder {
        val binding = SearchRecyclerviewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchListAdapter.ItemViewHolder, position: Int) {
        val searchedItem = items[position]
//        val imageLoader = ImageLoader.Builder(context1)
//
//        holder.thumnailImage.load(searchedItem.searchedVideo, imageLoader)

        holder.title.text = searchedItem.searchedTitle
        Glide.with(context1)
            .load(searchedItem.searchedVideo)
            .into(holder.thumnailImage)

    }

    override fun getItemCount(): Int = items.size

    inner class ItemViewHolder(binding: SearchRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        var thumnailImage: ImageView = binding.searchImgItem
        var title: TextView = binding.searchTxtItem
        override fun onClick(view: View?) {
            TODO("Not yet implemented")

            notifyItemChanged(position)
        }
    }
}