package com.example.wikisearch.database.entity

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide


@Entity(tableName = "WikiArticle")
data class WikiArticle (
    var articleId: Int,
    var articleTitle: String,
    var description: String?,
    var imageUrl: String?,
    var fullArticleUrl: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0


    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }

}