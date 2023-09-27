package com.example.my_media.home.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.my_media.R
import com.example.my_media.databinding.ItemVideosBinding

class HomePopularListAdapter(
    val itemClickListener: (HomePopularModel) -> Unit
): ListAdapter<HomePopularModel, HomePopularListAdapter.ViewHolder>(
    object: DiffUtil.ItemCallback<HomePopularModel>() {
        override fun areItemsTheSame(oldItem: HomePopularModel, newItem: HomePopularModel): Boolean {
            return oldItem.txtTitle == newItem.txtTitle
        }

        override fun areContentsTheSame(oldItem: HomePopularModel, newItem: HomePopularModel): Boolean {
            return oldItem == newItem
        }
    }
) {
    inner class ViewHolder(private val binding: ItemVideosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomePopularModel) = with(binding) {
            val s = item.imgThumbnail
            imgThumbnail.load(s) {
                error(R.drawable.ic_launcher_background)
            }
            txtTitle.text = item.txtTitle
            root.setOnClickListener {
                itemClickListener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

    }
}