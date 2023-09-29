package com.example.my_media.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.my_media.databinding.FavoriteItemBinding
import coil.load
import com.example.my_media.R
import com.example.my_media.home.popular.HomePopularModel

class MyVideoAdapter(val itemClickListener: (MyVideoModel) -> Unit) : ListAdapter<MyVideoModel,MyVideoAdapter.ViewHolder>(TestItemDiffCallback()){

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
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition.takeIf { it != RecyclerView.NO_POSITION }
                    ?: return@setOnClickListener
                itemClickListener(getItem(position))
            }
        }
        fun bindItems(item:MyVideoModel){
            binding.apply {
                item?.photo?.let {
                    binding.imageArea.load(it){
                        error(R.drawable.test)
                    }
                }
                ivTitleArea.text = item.title
            }
        }
    }
}