package com.example.my_media.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.my_media.databinding.ItemVideosBinding

class HomeListAdapter(
    val itemClickListener: (HomeModel) -> Unit
): ListAdapter<HomeModel, HomeListAdapter.ViewHolder>(
    object: DiffUtil.ItemCallback<HomeModel>() {
        override fun areItemsTheSame(oldItem: HomeModel, newItem: HomeModel): Boolean {
            return oldItem.id == newItem.id //추후 고유값으로 수정
        }

        override fun areContentsTheSame(oldItem: HomeModel, newItem: HomeModel): Boolean {
            return oldItem == newItem
        }
    }
) {
    inner class ViewHolder(private val binding: ItemVideosBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomeModel) = with(binding) {
            imgThumbnail.load(item.imgThumbnail)
            txtTitle.text = item.txtTitle
            root.setOnClickListener {
                itemClickListener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}