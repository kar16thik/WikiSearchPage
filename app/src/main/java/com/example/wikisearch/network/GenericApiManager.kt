package com.example.wikisearch.network




class GenericApiManager : BaseApiManager() {

    init {
        retrofit = initRetrofit(BASE_URL)
    }

    fun getWikiApiService(): WikiApiServices {
        return retrofit.create(WikiApiServices::class.java)
    }


}