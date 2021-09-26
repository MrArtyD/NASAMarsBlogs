package com.example.nasamarsblogs.ui.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nasamarsblogs.data.Blog
import com.example.nasamarsblogs.databinding.ItemBlogBinding

class BlogsAdapter : ListAdapter<Blog, BlogsAdapter.BlogViewHolder>(BLOG_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val binding = ItemBlogBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return BlogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val currentItem = getItem(position)

        currentItem?.let {
            holder.bind(it)
        }
    }

    class BlogViewHolder(val binding: ItemBlogBinding) : RecyclerView.ViewHolder(binding.root) {

        private var isItemClicked: Boolean = false

        init {
            binding.root.setOnClickListener {
                binding.run {
                    if (!isItemClicked){
                        tvTitle.maxLines = Int.MAX_VALUE
                        tvTitle.ellipsize = null
                    } else{
                        tvTitle.maxLines = 1
                        tvTitle.ellipsize = TextUtils.TruncateAt.END
                    }

                    isItemClicked = !isItemClicked
                }
            }
        }

        fun bind(blog: Blog) {
            binding.run {
                tvTitle.text = blog.title
            }
        }

    }

    companion object {
        private val BLOG_COMPARATOR = object : DiffUtil.ItemCallback<Blog>() {
            override fun areItemsTheSame(oldItem: Blog, newItem: Blog) =
                oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Blog, newItem: Blog) =
                oldItem == newItem

        }
    }

}