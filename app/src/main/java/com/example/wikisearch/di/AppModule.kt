package com.example.wikisearch.di

import com.example.wikisearch.database.DatabaseManager
import com.example.wikisearch.network.GenericApiManager
import com.example.wikisearch.wikisearch.repository.WikiSearchRepository
import com.example.wikisearch.wikisearch.viewmodel.WikiSearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module(override = true) {

    viewModel { WikiSearchViewModel() }

    single { GenericApiManager() }
    single { WikiSearchRepository() }
    single { DatabaseManager() }

}