package com.project.readarticleapp.ui.viewmodel.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.project.readarticleapp.R
import com.project.readarticleapp.data.network.networkModels.result.ArticleResult
import com.project.readarticleapp.databinding.FragmentArticleListBinding
import com.project.readarticleapp.ui.viewmodel.ArticleViewModel
import com.project.readarticleapp.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ArticleListFragment : Fragment() {

    @JvmField
    @Inject
    var networkUtils: NetworkUtils? = null

    //Instance of View Binding
    private lateinit var binding: FragmentArticleListBinding

    //View Model InStance
    //Both fragments retrieve the activity that contains them. That way, when the fragments each get the ViewModelProvider,
    // they receive the same SharedViewModel instance, which is scoped to this activity.
    private val viewModel by lazy { ViewModelProvider(requireActivity()).get(ArticleViewModel::class.java) }

    private lateinit var adapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentArticleListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = ArticleAdapter(::onItemClicked)

        viewModel.articleValueToLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController()
                    .navigate(ArticleListFragmentDirections.actionListFragmentToDetailsFragment())
                viewModel.clearArticleIdLiveData()

            }
        })
        val decoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        binding.recyclerView.addItemDecoration(decoration)
        initAdapter()
        setProgressBarVisibility()
        swipeToRefresh()

        return binding.root
    }

    private fun setProgressBarVisibility() {
        viewModel.progressBar.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.statusLoadingWheel.visibility = View.VISIBLE
            } else {
                binding.statusLoadingWheel.visibility = View.GONE
            }
        })
    }

    override fun onStart() {
        super.onStart()
        handleNetwork()
    }

    private fun onItemClicked(id: Int) {
        viewModel.setArticleId(id)
    }

    private fun swipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            sendRequestToFetchArticleData()
            showLoading(false)
        })
    }

    private fun sendRequestToFetchArticleData() {
        viewModel.getArticles()
    }

    private fun initAdapter() {
        binding.recyclerView.adapter = adapter
        viewModel.articleListData.observe(viewLifecycleOwner, Observer { it ->
            when (it) {
                is ArticleResult.Success -> {
                    showEmptyList(it.data.isEmpty())
                    adapter.submitList(it.data)
                }
                is ArticleResult.Error -> {
                    showError(it.error)
                    handleError(true, it.error)
                }
            }
        })
    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            binding.emptyView.visibility = View.VISIBLE
            binding.emptyView.text = getText(R.string.empty_list)
            binding.recyclerView.visibility = View.GONE
        } else {
            binding.emptyView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
    }

    private fun handleError(isEmptyList: Boolean = true, error: String = "") {
        showLoading(false)
        if (isEmptyList) {
            showEmptyList(isEmptyList)
        } else {
            showError(error)
        }
    }

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isLoading
    }


    private fun handleNetwork() {
        networkUtils!!.getNetworkLiveData().observe(this, { isConnected: Boolean? ->
            if (!isConnected!!) {
                binding.textViewNetworkStatus.text = getString(R.string.text_no_connectivity)
                binding.networkStatusLayout.visibility = View.VISIBLE
                binding.networkStatusLayout.setBackgroundColor(ResourcesCompat.getColor(
                    resources,
                    R.color.colorStatusNotConnected,
                    null
                ))
                binding.swipeRefreshLayout.isRefreshing = false
            } else {
                sendRequestToFetchArticleData()
                binding.textViewNetworkStatus.text = getString(R.string.text_connectivity)
                binding.networkStatusLayout.setBackgroundColor(ResourcesCompat.getColor(
                    resources, R.color.colorStatusConnected, null
                ))
                binding.networkStatusLayout.animate().alpha(1f)
                    .setStartDelay(ANIMATION_DURATION.toLong())
                    .setDuration(ANIMATION_DURATION.toLong())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            binding.networkStatusLayout.visibility = View.GONE
                        }
                    })
            }
        })
    }

    companion object {
        const val ANIMATION_DURATION = 1000
    }

}