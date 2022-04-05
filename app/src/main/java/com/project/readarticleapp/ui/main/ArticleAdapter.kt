package com.project.readarticleapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.readarticleapp.databinding.ListItemArticleBinding
import com.project.readarticleapp.model.ArticleModelList
import com.project.readarticleapp.utils.BindingAdapter

class ArticleAdapter(private val onItemClicked: (Int) -> Unit) :
    ListAdapter<ArticleModelList, ArticleAdapter.ViewHolder>(MovieDataDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userData = getItem(position)
        holder.bind(userData!!, onItemClicked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class MovieDataDiffCallback : DiffUtil.ItemCallback<ArticleModelList>() {
        override fun areItemsTheSame(
            oldItem: ArticleModelList,
            newItem: ArticleModelList,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ArticleModelList,
            newItem: ArticleModelList,
        ): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder private constructor(val binding: ListItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            userData: ArticleModelList,
            onItemClicked: (id: Int) -> Unit,
        ) {
            binding.articleTitleText.text = userData.title
            binding.articleSiteText.text = userData.site
            binding.articleSummaryText.text = userData.summary
            if (BindingAdapter.validUrl(userData.imageUrl)) {
                BindingAdapter.bindLoadImage(binding.moviesImageView, userData.imageUrl)
            }
            binding.root.setOnClickListener {
                onItemClicked(userData.id)
            }
            binding.invalidateAll()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemArticleBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}