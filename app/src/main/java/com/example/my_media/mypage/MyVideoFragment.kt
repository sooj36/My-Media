package com.example.my_media.mypage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.my_media.R
import com.example.my_media.databinding.FragmentMyVideoBinding
import com.example.my_media.detail.VideoDetailFragment
import com.example.my_media.main.MainSharedEventforLike
import com.example.my_media.main.MainSharedViewModel

class MyVideoFragment : Fragment() {
    companion object {
        fun newInstance() = MyVideoFragment()
    }

    private var _binding: FragmentMyVideoBinding? = null
    private val viewModel: MyVideoViewModel by viewModels { MyVideoViewModelFactory() }
    private val sharedViewModel: MainSharedViewModel by activityViewModels()
    private val binding get() = _binding!!
    private val adapter: MyVideoAdapter by lazy { binding.favoriteRvArea.adapter as MyVideoAdapter }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setUpRecylclerView()
        initViewModel()

        binding.apply {
            gitgubArea.setOnClickListener {
                openLinkGit()
                notionArea.setOnClickListener {
                    openLinkNotion()
                }
            }
        }
    }

    private fun initViewModel() {
        with(viewModel) {
            likeList.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
        with(sharedViewModel) {
            likeEvent.observe(viewLifecycleOwner) {
                it.forEach { event ->
                    when (event) {
                        is MainSharedEventforLike.AddLikeItem -> {
                            viewModel.addLikeItem(event.item)
                        }

                        is MainSharedEventforLike.RemoveLikeItem -> {
                            viewModel.removeLikeItem(event.item)
                        }
                    }
                }
            }
        }
    }

    private fun initView() = with(binding) {
    }

    private fun openLinkGit() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/12-team-project"))
        startActivity(intent)
    }

    private fun openLinkNotion() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://teamsparta.notion.site/12-S-A-f79dc026055d4ec98d97ff1e3bffe057")
        )
        startActivity(intent)
    }

    private fun setUpRecylclerView() {
        binding.apply {
            favoriteRvArea.adapter = MyVideoAdapter { item ->
                val fragment = VideoDetailFragment.newInstance(item.toHomePopularModel())

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, fragment)
                    .addToBackStack(null)
                    .commit()
            }
            favoriteRvArea.layoutManager = GridLayoutManager(context, 2)
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
