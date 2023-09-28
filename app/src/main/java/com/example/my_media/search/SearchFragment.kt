package com.example.my_media.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.my_media.R
import com.example.my_media.databinding.FragmentSearchBinding
import com.example.my_media.mypage.MyVideoFragment

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    lateinit var adapter: SearchListAdapter
    lateinit var searchRecyclerView: RecyclerView
    lateinit var item: SearchModel

    private val viewModel: SearchViewModel by viewModels {
        SearchViewModelFactory()
    }

    private val searchListAdapter by lazy {
        SearchListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        searchRecyclerView = binding.recyclerviewResult
        adapter = SearchListAdapter()
        searchRecyclerView.adapter = adapter

        adapter.itemClick = object : SearchListAdapter.ItemClick {
            override fun onClick(position: Int) {
                val clickedItem = searchListAdapter.currentList[position]
                val moveFragment = MyVideoFragment()
                val bundle = Bundle()
                bundle.putString("title", clickedItem.searchedTitle)
                bundle.putString("videoUrl", clickedItem.searchedVideo)
                moveFragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.search_recyclerview_item, moveFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }





        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        recyclerviewResult.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = searchListAdapter
            itemAnimator = null
        }

        btnSearch.setOnClickListener {
            performSearch()
        }
    }

    private fun initViewModel() = with(viewModel) {
        list.observe(viewLifecycleOwner) {
            searchListAdapter.submitList(it)
        }
    }

    private fun performSearch() {
        val query = binding.edtSearch.text.toString()
        if (query.isNotEmpty()) {
            viewModel.getSearchVideo(query)
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.search_txt_find_keyword),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
