package com.example.my_media.detail

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
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
import com.example.my_media.search.SearchModel
import com.example.my_media.search.toHomePopularModel
import com.example.my_media.util.showToast

class VideoDetailFragment : Fragment() {
    companion object {
        private const val ITEM_KEY = "itemKey"
        fun newInstance(item: Parcelable) = VideoDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ITEM_KEY, item)
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

        arguments?.let { args ->
            val item: Parcelable? = args.getParcelable(ITEM_KEY)
            when (item) {
                is HomePopularModel -> {
                    init(item)
                    shareUrl(item.imgThumbnail)
                }
                is SearchModel -> {
                    init(item.toHomePopularModel())
                    shareUrl(item.searchedVideo)
                }
                else -> return
            }
        }
    }
    private fun init(item: Any) {
        when (item) {
            is HomePopularModel -> {
                initViewModel(item)
                initView(item)
                shareUrl(item.imgThumbnail)
            }
            is SearchModel -> {
                initViewModel(item.toHomePopularModel())
                initView(item.toHomePopularModel())
                shareUrl(item.searchedVideo)
            }
            else -> return
        }
    }

    private fun initViewModel(item: HomePopularModel) {
        item.isLiked = sharedViewModel.getLikeStatus(item.txtTitle)
        updateLikeButtonUI(item.isLiked)
    }

    private fun initView(item: HomePopularModel) = with(binding) {
        likeBtn.setOnClickListener {
            val isLiked = sharedViewModel.getLikeStatus(item.txtTitle)
            val newItem = item.copy(isLiked = !isLiked)
            sharedViewModel.toggleLikeItem(newItem)
            if (isLiked) {
                requireContext().showToast(
                    requireContext().getString(R.string.toast_txt_unlike),
                    Toast.LENGTH_LONG
                )
            } else
                requireContext().showToast(
                    requireContext().getString(R.string.toast_txt_like),
                    Toast.LENGTH_LONG
                )
            updateLikeButtonUI(newItem.isLiked)
        }
        titleArea.text = item.txtTitle
        desArea.text = item.txtDescription
        thumnailArea.load(item.imgThumbnail) {
            error(R.drawable.test)
        }
    }

    private fun updateLikeButtonUI(isLiked: Boolean) = with(binding) {
        if (isLiked) {
            likeBtn.apply{setAnimation(R.raw.like)
            playAnimation()
            }
        } else {
            likeBtn.apply {
                setAnimation(R.raw.like)
                setMinAndMaxFrame(11, 14)
                playAnimation()
            }
        }
    }

    private fun shareUrl(url: String) {
        binding.sharedBtn.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, url)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(intent, "이미지 URL 공유"))
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
