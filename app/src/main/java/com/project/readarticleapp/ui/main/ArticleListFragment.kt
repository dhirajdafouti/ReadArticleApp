package com.project.readarticleapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.project.readarticleapp.R
import com.project.readarticleapp.ui.viewmodel.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArticleListFragment : Fragment() {

    val viewModel by lazy { ViewModelProvider(requireActivity()).get(ArticleViewModel::class.java) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article_list, container, false)
    }


}