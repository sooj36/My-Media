package com.example.my_media.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.my_media.BuildConfig
import com.example.my_media.data.Response
import com.example.my_media.data.RetrofitClient.apiService
import com.example.my_media.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.Query

class SearchFragment : Fragment() {

    private lateinit var adapter: SearchListAdapter
    private lateinit var adapterimage: StaggeredGridLayoutManager
    private lateinit var context1: Context
    private var item: ArrayList<SearchModel> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context1 = context
    }


    companion object {
        fun newInstance() = SearchFragment()
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        setupView()

        binding.btnSearch.setOnClickListener {
            performSearch()
        }

        return binding.root
    }

    private fun setupView() {
        adapterimage = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerviewResult.layoutManager = adapterimage

        adapter = SearchListAdapter(context1)
        binding.recyclerviewResult.adapter = adapter
        binding.recyclerviewResult.itemAnimator = null
    }

    private fun performSearch() {
        val query = binding.edtSearch.text.toString()
        if (query.isNotEmpty()) {
            SearchVideoResults(query)
        } else {
            Toast.makeText(context1, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initView()
//        SearchVideoResults()
    }


//    private fun initView() = with(binding) {
//
//    }

    private fun SearchVideoResults(query: String) {
        apiService.video_search(
            part = "snippet",
            chart = "mostPopular",
            maxResults = 10,
            regionCode = "KR",
            apiKey = "${BuildConfig.API_KEY}"
        )?.enqueue(object : Callback<Response> {
            override fun onResponse(
                call: Call<Response>, response: retrofit2.Response<Response>
            ) {
                Log.d("ww", "$response")
                if (response.isSuccessful) {
                    response.body()?.items?.forEach {

                        Log.d(
                            "YoutubeApi",
                            "Title${it.snippet.title}"
                        )
                        val title = it.snippet.title
                        val thumbnail = it.snippet.thumbnails.high.url

                        item.add(SearchModel(title, thumbnail))
                    }
                } else {
                    Log.e("YoutubeApi", "Error: ${response.errorBody()}")
                }
                adapter.items = item
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                Log.e("YoutubeApi", "Error: ${t.printStackTrace()}")

            }
        }

        )

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
