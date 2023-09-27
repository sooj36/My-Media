package com.example.my_media.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.my_media.R
//import com.example.my_media.data.RemoteDataSource
import com.example.my_media.data.RepositoryImpl
import com.example.my_media.data.RetrofitClient
import com.example.my_media.data.RetrofitInterface
import com.example.my_media.databinding.FragmentHomeBinding
import com.google.android.material.chip.Chip

class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var adapter: HomeListAdapter
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels() {
        HomeViewModelFactory(RetrofitClient.retrofit.create(RetrofitInterface::class.java))


    }

    private val homeListAdapter by lazy {
        HomeListAdapter(
            itemClickListener = { item ->
                //Todo (VideoDetailFragment 로 데이터 전달)
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
        Log.d("sooj", "onViewCreate")

        val chipTravel = view.findViewById<Chip>(R.id.chip_travel)
        val chipMusic = view.findViewById<Chip>(R.id.chip_music)
        val chipGame = view.findViewById<Chip>(R.id.chip_game)
        val chipSleep = view.findViewById<Chip>(R.id.chip_sleep)


        viewModel.getPopularVideo()
    }

    private fun initView() = with(binding) {
        recyclerViewPopular.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = homeListAdapter
        }
        recyclerViewSubscribe.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            adapter = homeListAdapter
            Log.d("sooj", "initView")
        }

        when (chipGroup.checkedChipId) {
            R.id.chip_travel -> {

            }

            R.id.chip_music -> {

            }

            R.id.chip_game -> {

            }

            R.id.chip_sleep -> {

            }
        }
    }

    private fun initViewModel() = with(viewModel) {
        list.observe(viewLifecycleOwner) {list -> //람다 표현식
            homeListAdapter.submitList(list) // it을 통해 값이 들어옴
            Log.d("sooj", "initViewModel")
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
