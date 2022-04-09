package com.project.readarticleapp.ui.viewmodel.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.readarticleapp.R
import com.project.readarticleapp.data.network.networkModels.result.ArticleDetailsResult
import com.project.readarticleapp.databinding.FragmentArticleDetailsBinding
import com.project.readarticleapp.ui.viewmodel.ArticleViewModel
import com.project.readarticleapp.utils.BindingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {

    //View Model InStance
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(ArticleViewModel::class.java) }

    private lateinit var binding: FragmentArticleDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentArticleDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.statusLoadingWheel.visibility = View.VISIBLE
            } else {
                binding.statusLoadingWheel.visibility = View.GONE
            }
        })
        sendRequestToFetchMovieData()
        updateWidgets()
        return binding.root
    }

    private fun updateWidgets() {
        viewModel.articleDetailsData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ArticleDetailsResult.Success -> {
                    if (BindingAdapter.validUrl(it.data.imageUrl)) {
                        BindingAdapter.bindLoadImage(binding.mainImageView, it.data.imageUrl)
                    }
                    val url = it.data.url
                    binding.articlePublishedText.text = it.data.publishedAt
                    binding.articleFeaturedText.text = it.data.featured.toString()
                    binding.articleIdText.text = it.data.id.toString()
                    binding.articleUpdatedText.text = it.data.updatedAt
                    binding.articleWeb.setOnClickListener {
                        val intent = android.content.Intent(
                            android.content.Intent.ACTION_VIEW,
                            android.net.Uri.parse(url)
                        )
                        context?.startActivity(intent)
                    }

                    showMessage(getString(R.string.success))
                }
                is ArticleDetailsResult.Error ->
                    showMessage(it.error)
            }

        })
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun sendRequestToFetchMovieData() {
        viewModel.getArticleDetails(viewModel.getItemId())
    }


}