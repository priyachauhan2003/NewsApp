package com.techmania.newsapp.repository

import com.techmania.newsapp.api.RetrofitInstance
import com.techmania.newsapp.db.ArticleDao
import com.techmania.newsapp.db.ArticleDatabase
import com.techmania.newsapp.models.Article

class NewsRepository(val db : ArticleDatabase) {
    suspend fun getHeadlines(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getHeadlines(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsert(article : Article) = db.getArticleDao().upsert(article)

    fun getFavouriteNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)
}