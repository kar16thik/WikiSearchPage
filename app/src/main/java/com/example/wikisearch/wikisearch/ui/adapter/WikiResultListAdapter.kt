package com.example.wikisearch.wikisearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.wikisearch.database.entity.WikiArticle
import com.example.wikisearch.databinding.AdapterArticleListBinding
import com.example.wikisearch.network.response.WikiArticleData

class WikiResultListAdapter  ( val onItemSelect:(wikiArticleData: WikiArticle)->Unit)
    : RecyclerView.Adapter<WikiResultListAdapter.ViewHolder>() {

    lateinit var mWikiArticleData: WikiArticle
    var mItemsList = ArrayList<WikiArticle>()
    override fun getItemCount(): Int {
        return mItemsList.size
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterArticleListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        mWikiArticleData = mItemsList.get(position)

        with(holder){
            binding.responseData=mWikiArticleData
            binding.root.setOnClickListener {
                onItemSelect(mItemsList.get(position))
            }

        }
     }

    fun updateArticleData(articleList:ArrayList<WikiArticle>){
        mItemsList=articleList
        notifyDataSetChanged()
    }
    fun clearData(){
        mItemsList.clear()
        notifyDataSetChanged()
    }


    inner class ViewHolder(val binding: AdapterArticleListBinding) :
        RecyclerView.ViewHolder(binding.root){

    }

}