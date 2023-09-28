package com.example.my_media.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.my_media.R
import com.example.my_media.databinding.FragmentVideoDetailBinding
import com.example.my_media.home.popular.HomePopularModel
import com.example.my_media.main.MainSharedViewModel
import com.example.my_media.mypage.MyVideoViewModel
import com.example.my_media.util.showToast
import kotlin.math.log

class VideoDetailFragment : Fragment() {
    companion object {
        fun newInstance(item: HomePopularModel) = VideoDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable("item", item)
            }
        }
    }

    private val sharedViewModel: MainSharedViewModel by activityViewModels()
    private var _binding: FragmentVideoDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item: HomePopularModel? = arguments?.getParcelable("item")
        item?.let {
            initViewModel(it)
        }
    }

    private fun initViewModel(item: HomePopularModel) {
        item.isLiked = sharedViewModel.getLikeStatus(item.txtTitle)

        binding.apply {
            titleArea.text = item.txtTitle
            binding.desArea.text = item.txtDescription
            binding.thumnailArea.load(item.imgThumbnail) {
                error(R.drawable.test)
            }
        }
        updateLikeButtonUI(item.isLiked)

        binding.likeBtn.setOnClickListener {
            val isLiked = sharedViewModel.getLikeStatus(item.txtTitle)
            val newItem = item.copy(isLiked = !isLiked)
            sharedViewModel.toggleLikeItem(newItem)
            if (isLiked) {
                context?.showToast("좋아요 리스트에서 제거 되었습니다", Toast.LENGTH_LONG)
            } else
                context?.showToast("좋아요 리스트에 추가 되었습니다", Toast.LENGTH_LONG)
            updateLikeButtonUI(newItem.isLiked)
        }
    }

    private fun updateLikeButtonUI(isLiked: Boolean) {
        binding.likeBtn.setImageResource(
            if (isLiked) R.drawable.ic_like else R.drawable.ic_mtlike
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
