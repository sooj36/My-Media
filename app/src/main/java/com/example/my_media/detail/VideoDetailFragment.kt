package com.example.my_media.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.my_media.databinding.FragmentVideoDetailBinding
import com.example.my_media.main.MainSharedEventforLike
import com.example.my_media.main.MainSharedViewModel

class VideoDetailFragment : Fragment() {
    companion object {
        fun newInstance() = VideoDetailFragment()
    }
    private val viewModel : MainSharedViewModel by activityViewModels()
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

        viewModel.likeEvent.observe(viewLifecycleOwner){event->
            when(event){
                is MainSharedEventforLike.AddLikeItem -> {

                }
                is MainSharedEventforLike.RemoveLikeItem -> {

                }
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
