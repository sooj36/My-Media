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
import com.example.my_media.main.MainSharedViewModelFactory
import com.example.my_media.search.SearchModel
import com.example.my_media.search.toHomePopularModel
import com.example.my_media.util.showToast
import kotlin.math.max

class VideoDetailFragment : Fragment() {
    companion object {
        private const val ITEM_KEY = "itemKey"
        fun newInstance(item: Parcelable) = VideoDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ITEM_KEY, item)
            }
        }
    }

    private val sharedViewModel: MainSharedViewModel by activityViewModels {
        MainSharedViewModelFactory(
            requireContext()
        )
    }
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
                initView(item)
                initViewModel(item)
                shareUrl(item.imgThumbnail)
            }

            is SearchModel -> {
                initView(item.toHomePopularModel())
                initViewModel(item.toHomePopularModel())

                shareUrl(item.searchedVideo)
            }

            else -> return
        }
    }

    private fun initView(item: HomePopularModel) = with(binding) {
        likeBtn.setOnClickListener {
            val isLiked = sharedViewModel.getLikeStatus(item.txtTitle)
            val newItem = item.copy(isLiked = !isLiked)

            newItem.likeCount = if (!isLiked) {
                (newItem.likeCount ?: 0) + 1
            } else {
                max(0, (newItem.likeCount ?: 0) - 1) //max를 이용해서 0과비교 0보다 작아지면 안대니깐
            }
            sharedViewModel.toggleLikeItem(newItem)

            val text = if (!isLiked) {
                requireContext().getString(R.string.detail_toast_like)
            } else {
                requireContext().getString(R.string.detail_toast_unlike)
            }
            requireContext().showToast(text, Toast.LENGTH_SHORT)

            updateLikeButtonUI(!isLiked)
            likeCount.text = formatCount(newItem.likeCount)
        }

        titleArea.text = item.txtTitle
        desArea.text = item.txtDescription
        likeCount.text = formatCount(item.likeCount)
        viewCount.text = formatCount(item.viewCount)
        thumbnailArea.load(item.imgThumbnail) {
            error(R.drawable.ic_launcher_background)
        }

    }

    private fun initViewModel(item: HomePopularModel) {
        item.isLiked = sharedViewModel.getLikeStatus(item.txtTitle) //호출해서 좋아요 상태확인
        updateLikeButtonUI(item.isLiked)
    }

    private fun updateLikeButtonUI(isLiked: Boolean) = with(binding) {
        if (isLiked) {
            likeBtn.apply {
                setAnimation(R.raw.like)
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

    fun formatCount(count: Long?): String {
        if (count == null) return "0"

        val absoluteCount = Math.abs(count) //절대값계산
        val countString = when {
            absoluteCount < 1000 -> { //1000미만일경우 카운트 문자열반환
                count.toString()
            }

            absoluteCount < 1_000_000 -> {
                String.format(
                    "%.1f천",
                    absoluteCount / 1000.0
                ) //카운트를 1000으로 나눈 후 소수첫쨎자리까지 반올림하여 천단위로 표시
            }

            else -> {
                String.format(
                    "%.1f만",
                    absoluteCount / 1_000_000.0
                ) //1,000,000으로 나눈 후 소수 첫째자리까지 반올림하여 만 단위
            }
        }
        return countString
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
