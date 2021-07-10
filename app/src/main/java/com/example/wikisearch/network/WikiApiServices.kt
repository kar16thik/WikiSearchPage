package com.example.wikisearch.network

import com.example.wikisearch.network.response.WikiSearchResponse
import com.example.wikisearch.network.response.WikiSearchResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WikiApiServices {

    @GET(API_WIKI_SEARCH)
    fun searchWikiArticle(@Query("gpssearch") searchValue:String): Call<WikiSearchResponse>
}