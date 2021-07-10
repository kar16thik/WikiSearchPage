package com.example.wikisearch.database

import androidx.room.Room
import com.example.wikisearch.BaseApplication

import org.koin.core.component.KoinComponent


class DatabaseManager : KoinComponent {
    val dbInstance: AppDatabase = Room.databaseBuilder(
        BaseApplication.applicationContext()
    , AppDatabase::class.java, "wiki-database").build()


}