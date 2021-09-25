package com.example.nasamarsblogs.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.nasamarsblogs.R
import com.example.nasamarsblogs.databinding.FragmentBlogsBinding
import com.example.nasamarsblogs.ui.adapters.BlogsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlogsFragment : Fragment(R.layout.fragment_blogs) {

    private var _binding: FragmentBlogsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<BlogsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlogsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BlogsAdapter()

        binding.run {
            rvBlogs.adapter = adapter
            btnLoadBlogs.setOnClickListener {
                viewModel.getBlogs()
            }

            viewModel.isBlogsLoading.observe(viewLifecycleOwner){
                progressBar.isVisible = it
            }

            viewModel.loadingError.observe(viewLifecycleOwner){
                tvError.isVisible = it
            }
        }

        viewModel.blogs.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}