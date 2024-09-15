package com.techmania.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.techmania.newsapp.R
import com.techmania.newsapp.databinding.FragmentArticleBinding
import com.techmania.newsapp.models.Article
import com.techmania.newsapp.ui.NewsActivity
import com.techmania.newsapp.ui.NewsViewModel

class ArticleFragment : Fragment(R.layout.fragment_article) {
    lateinit var newsViewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()
    lateinit var binding: FragmentArticleBinding
    private var article: Article? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentArticleBinding.bind(view)

        newsViewModel = (activity as NewsActivity).newsViewModel
        article = arguments?.getSerializable("article") as Article?

        article?.let {
            binding.webView.apply {
                webViewClient = WebViewClient()
                it.url?.let { url ->
                    loadUrl(url)  // Load the article URL into the WebView
                }
            }

            // Handle adding to favorites
            binding.fab.setOnClickListener {
                newsViewModel.addToFavourites(article!!)
                Snackbar.make(view, "Added to favourites", Snackbar.LENGTH_SHORT).show()
            }
        } ?: run {
            // Handle the case where the article is null (optional)
            Snackbar.make(view, "Article not found", Snackbar.LENGTH_SHORT).show()
        }
    }
}