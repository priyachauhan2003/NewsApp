package com.techmania.newsapp.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.techmania.newsapp.R
import com.techmania.newsapp.models.Article

class NewsAdapter:RecyclerView.Adapter<NewsAdapter.ArticleViewHolder> (){

    inner class ArticleViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    lateinit var articleImage: ImageView
    lateinit var articleTitle: TextView
    lateinit var articleSource: TextView
    lateinit var articleDescription: TextView
    lateinit var articleDateTime: TextView

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
           return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        articleImage = holder.itemView.findViewById(R.id.articleImage)
        articleTitle = holder.itemView.findViewById(R.id.articleTitle)
        articleSource = holder.itemView.findViewById(R.id.articleSource)
        articleDescription = holder.itemView.findViewById(R.id.articleDescription)
        articleDateTime = holder.itemView.findViewById(R.id.articleDateTime)

        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(articleImage)
            articleSource.text = article.source?.name ?: "Unknown Source"
            articleDescription.text = article.description ?: "No description available"
            articleTitle.text = article.title ?: "No title available"
            articleDateTime.text = article.publishedAt ?: "Unknown Date"

            setOnClickListener {
                onItemClickListener?.let {
                    it(article)
                }
            }
        }
    }
    fun setItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}