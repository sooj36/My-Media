package com.example.my_media.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.my_media.databinding.FavoriteItemBinding

class MyVideoAdapter() : ListAdapter<MyVideoModel,MyVideoAdapter.ViewHolder>(TestItemDiffCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyVideoAdapter.ViewHolder {
        val binding = FavoriteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyVideoAdapter.ViewHolder, position: Int) {

        holder.bindItems(getItem(position))


    }
    class TestItemDiffCallback : DiffUtil.ItemCallback<MyVideoModel>(){
        override fun areItemsTheSame(oldItem: MyVideoModel, newItem: MyVideoModel): Boolean {
         return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MyVideoModel, newItem: MyVideoModel): Boolean {
          return oldItem == newItem
        }

    }
    inner class ViewHolder(private val binding: FavoriteItemBinding) :RecyclerView.ViewHolder(binding.root){
        fun bindItems(item:MyVideoModel){
            binding.apply {
                imageArea.setImageResource(item.photo)
                titleArea.text = item.title

            }
        }
    }

}