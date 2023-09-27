package com.example.my_media.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.my_media.R
import com.example.my_media.databinding.FragmentVideoDetailBinding
import com.example.my_media.home.HomeModel
import com.example.my_media.main.MainSharedEventforLike
import com.example.my_media.main.MainSharedViewModel
import kotlin.math.log

class VideoDetailFragment : Fragment() {
    companion object {
        fun newInstance() = VideoDetailFragment()
    }
    private val sharedViewModel : MainSharedViewModel by activityViewModels()
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



        binding.sharedBtn.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action =Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT,"나의 친구들에게 공유하세요!")
                type ="text/plain"
            }
            val shareIntent =Intent.createChooser(sendIntent,null)
            startActivity(shareIntent)
        }


//        binding.likeBtn.setOnClickListener {
//            get?.let { item ->
//                item.isLiked = !item.isLiked
//                if (item.isLiked) {
//                    Log.d("jun","토글변화 ${item.isLiked}")
//                    binding.likeBtn.setImageResource(R.drawable.ic_like)
//                } else {
//                    binding.likeBtn.setImageResource(R.drawable.ic_mtlike)
//                }
//                sharedViewModel.toggleLikeItem(item)
//            }
//        }

    }

    private fun initView() = with(binding) {

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
