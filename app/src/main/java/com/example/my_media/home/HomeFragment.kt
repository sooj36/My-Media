package com.example.my_media.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.my_media.R
import com.example.my_media.databinding.FragmentHomeBinding
import com.example.my_media.detail.VideoDetailFragment
import com.example.my_media.home.popular.HomePopularListAdapter
import com.example.my_media.home.subscribe.HomeSubscribeListAdapter

class HomeFragment : Fragment() {
    companion object {
        fun newInstance(accessToken: String): HomeFragment {
            val args = Bundle()
            args.putString("AccessToken", accessToken)
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private val viewModel: HomeViewModel by viewModels() {
        HomeViewModelFactory()
    }

    private val homeSubscribeListAdapter by lazy {
        HomeSubscribeListAdapter()
    }

    private val homePopularListAdapter by lazy {
        HomePopularListAdapter(requireContext(),
            itemClickListener = { item ->
                val fragment = VideoDetailFragment.newInstance(item)

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        recyclerViewSubscribe.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = homeSubscribeListAdapter
        }
        recyclerViewPopular.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = homePopularListAdapter
        }

        val accessToken = arguments?.getString("AccessToken") ?: ""
        viewModel.getSubscribeList("Bearer $accessToken") //구독 리스트 불러오기

        viewModel.getPopularVideo(
            "Bearer $accessToken", "0"
        )

        var videoCategoryId = ""
        chipGroup.setOnCheckedChangeListener { group, checkedId ->

            when (chipGroup.checkedChipId) {
                R.id.chip_all -> {
                    videoCategoryId = "0"
                    Log.d("sooj", "$videoCategoryId =")
                }
                R.id.chip_animal -> {
                    videoCategoryId = "15"
                    Log.d("sooj", "Pets & Animals")
                }

                R.id.chip_music -> {
                    videoCategoryId = "10"
                    Log.d("sooj", "Music")
                }

                R.id.chip_game -> {
                    videoCategoryId = "20"
                    Log.d("sooj", "Gaming")
                }

                R.id.chip_comedy -> {
                    videoCategoryId = "23"
                    Log.d("sooj", "Comedy")
                }

                else -> ""
            }
            viewModel.getPopularVideo("Bearer $accessToken", videoCategoryId)
            Log.d(
                "sooj",
                "chip 초기화면 ${viewModel.getPopularVideo("Bearer $accessToken", videoCategoryId)}"
            )
        }
    }

    private fun initViewModel() = with(viewModel) {
        subscribeList.observe(viewLifecycleOwner) {
            homeSubscribeListAdapter.submitList(it)
        }

        popularVideoList.observe(viewLifecycleOwner) {
            homePopularListAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}