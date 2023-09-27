package com.example.my_media.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import coil.load
import com.example.my_media.R
import com.example.my_media.databinding.FragmentVideoDetailBinding
import com.example.my_media.home.popular.HomePopularModel
import com.example.my_media.main.MainSharedViewModel

class VideoDetailFragment : Fragment() {
    companion object {
        fun newInstance(item: HomePopularModel) = VideoDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable("item", item)//객체전달
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
        initView()
        //넘겨온 아이템객체받기
        val item: HomePopularModel? = arguments?.getParcelable("item")
        val videoId = item?.txtTitle ?: return

        binding.apply {
            titleArea.text = item?.txtTitle
            desArea.text = item?.txtDescription
        }
        item?.imgThumbnail?.let {
            binding.thumnailArea.load(it){
                error(R.drawable.test)
            }
        }


        binding.sharedBtn.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "나의 친구들에게 공유하세요!")
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        binding.likeBtn.run  {//apply보단 run이 간지
            setImageResource(if (sharedViewModel.getLikeStatus(videoId)) R.drawable.ic_like else R.drawable.ic_mtlike)

            setOnClickListener {
                item.isLiked = !item.isLiked
                val currentStatus = !sharedViewModel.getLikeStatus(videoId)
                sharedViewModel.updateLikeStatus(videoId, currentStatus)
                sharedViewModel.toggleLikeItem(item)//가독성때문에 with로 안묶음
                setImageResource(if (currentStatus) R.drawable.ic_like else R.drawable.ic_mtlike)

            }
        }
    }

    private fun initView() = with(binding) {

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
