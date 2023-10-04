package com.example.my_media.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.my_media.R
import com.example.my_media.databinding.FragmentHomeBinding
import com.example.my_media.detail.VideoDetailFragment
import com.example.my_media.home.popular.HomePopularListAdapter
import com.example.my_media.home.subscribe.HomeSubscribeListAdapter
import com.example.my_media.util.UserManager

class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var videoCategoryId: String? = null

    private val viewModel: HomeViewModel by activityViewModels() {
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

        val accessToken = UserManager.getAccessToken()

        val chips = listOf(
            R.id.chip_all to "0",
            R.id.chip_animal to "15",
            R.id.chip_music to "10",
            R.id.chip_game to "20",
            R.id.chip_comedy to "23"
        )

        viewModel.getSubscribeList("Bearer $accessToken") //구독 리스트 불러오기
        if(videoCategoryId.isNullOrBlank()) {
            viewModel.getPopularVideo("Bearer $accessToken","0") //인기 동영상 불러오기
        }

        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            if(checkedId != -1) {
                videoCategoryId = chips.find { it.first == checkedId }?.second
                viewModel.getPopularVideo("Bearer $accessToken", videoCategoryId ?: "0")
            }
        }
    }

    private fun initViewModel() = with(viewModel) {
        isEmptySubscribe.observe(viewLifecycleOwner) { isEmpty ->
            if (isEmpty) {
                binding.txtEmptySubscribe.visibility = View.VISIBLE
            } else {
                binding.txtEmptySubscribe.visibility = View.INVISIBLE
            }
        }
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