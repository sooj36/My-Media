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

        viewModel.getPopularVideo("Bearer $accessToken", "0")

        val chips = listOf(
            R.id.chip_all to "0",
            R.id.chip_animal to "15",
            R.id.chip_music to "10",
            R.id.chip_game to "20",
            R.id.chip_comedy to "23"
        )

        var videoCategoryId = ""
        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            for (i in chips.indices) {
                if (checkedId == chips.get(i).first) {
                    videoCategoryId = chips.get(i).second
                    Log.d("sooj", "chips click ${videoCategoryId}")
                }
            }
            viewModel.getPopularVideo("Bearer $accessToken", videoCategoryId)
            Log.d("sooj", " test : $videoCategoryId ")
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