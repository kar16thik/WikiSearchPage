package com.example.wikisearch.database

import androidx.room.Database
import androidx.room.RoomDatabase

import com.example.wikisearch.database.dao.WikiArticleDao
import com.example.wikisearch.database.entity.WikiArticle

@Database(entities = arrayOf(WikiArticle::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getWikiArticleDao(): WikiArticleDao
}