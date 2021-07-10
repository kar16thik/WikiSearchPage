package com.example.wikisearch.wikisearch.repository

import com.example.wikisearch.database.DatabaseManager
import com.example.wikisearch.database.entity.WikiArticle
import com.example.wikisearch.network.GenericApiManager
import com.example.wikisearch.network.response.WikiSearchResponse
import io.reactivex.Flowable
import io.reactivex.Single

import io.reactivex.subjects.PublishSubject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WikiSearchRepository : KoinComponent {
    private val apiMananger: GenericApiManager by inject()
    private val databaseManager: DatabaseManager by inject()

    fun getWikiSearchData(searchText:String): PublishSubject<WikiSearchResponse> {
        val responseSubject = PublishSubject.create<WikiSearchResponse>()
        val dashboardApiService = apiMananger.getWikiApiService()

        val call: Call<WikiSearchResponse> = dashboardApiService.searchWikiArticle(searchText)
        call.enqueue(object : Callback<WikiSearchResponse> {
            override fun onResponse(call: Call<WikiSearchResponse>, response: Response<WikiSearchResponse>) {

                var wikiSearchList = response.body()
                if (wikiSearchList != null) {
                    responseSubject.onNext(wikiSearchList)
                }
                responseSubject.onComplete()
            }
            override fun onFailure(call: Call<WikiSearchResponse>, t: Throwable) {

                responseSubject.onError(Throwable((t.message)))
                responseSubject.onComplete()
            }
        })

        return responseSubject
    }

    fun saveWikiReadArticle(wikiArticle: WikiArticle): Single<Long> {
        return  databaseManager.dbInstance.getWikiArticleDao().saveArticle(wikiArticle)
    }
    fun getAllReadArticleData(): Flowable<List<WikiArticle>> {

        return databaseManager.dbInstance.getWikiArticleDao().getAllArticle()
    }

    fun checkIsArticleAlredySaved(articleId:Int):Single<List<WikiArticle>>{
        return databaseManager.dbInstance.getWikiArticleDao().checkArticlesaved(articleId)
    }
}