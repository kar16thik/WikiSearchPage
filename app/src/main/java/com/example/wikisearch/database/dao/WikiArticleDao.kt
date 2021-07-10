package com.example.wikisearch.database.dao

import androidx.room.*
import com.example.wikisearch.database.entity.WikiArticle
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface WikiArticleDao{

    @Insert
    fun saveArticle(wikiArticle: WikiArticle): Single<Long>

    @Query("SELECT * FROM WikiArticle")
    fun getAllArticle(): Flowable<List<WikiArticle>>

    @Query("SELECT * FROM WikiArticle w where w.articleId=:articleId")
    fun checkArticlesaved(articleId:Int): Single<List<WikiArticle>>

}