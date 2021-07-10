package com.example.wikisearch.wikisearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.wikisearch.database.entity.WikiArticle
import com.example.wikisearch.network.response.WikiArticleData
import com.example.wikisearch.network.response.WikiSearchResponse
import com.example.wikisearch.wikisearch.repository.WikiSearchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WikiSearchViewModel :  ViewModel(), KoinComponent {

    private var mSavedWikiArticleLiveData: MutableLiveData<ArrayList<WikiArticle>> = MutableLiveData()
    private var mErrorLiveData: MutableLiveData<String> = MutableLiveData()
    private val mWikiSearchRepository:WikiSearchRepository by inject()

    //This function will make api call to get wiki pages
    fun getWikiSearchData(searchText:String){
        mWikiSearchRepository.getWikiSearchData(searchText)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = { },
                onNext = { it ->
                  if(it.wikiSearchData!=null) {
                      mSavedWikiArticleLiveData.value = createWikiArticleList(it.wikiSearchData.wikiArticleData)
                  }else{
                      mSavedWikiArticleLiveData.value=ArrayList()
                  }
                         },
                onError = {
                    mErrorLiveData.value=it.message
                }
            )
    }


    //This function will save selected wiki pages
    fun saveReadWikiArticle(wikiArticle: WikiArticle){
        mWikiSearchRepository.saveWikiReadArticle(wikiArticle)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {

                },
                onError = {
                    mErrorLiveData.value=it.message
                }
            )

    }

    //This function get all saved  wiki pages
    fun getSavedArticleData(){
        mWikiSearchRepository.getAllReadArticleData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onComplete = { },
                onNext = { it ->
                    mSavedWikiArticleLiveData.setValue(it as ArrayList<WikiArticle>?)

                },
                onError = {
                    mErrorLiveData.value=it.message
                }
            )

    }

    //This function check is selected wiki pages alredy saved
    fun checkArticleSaved(wikiArticle: WikiArticle){
        mWikiSearchRepository.checkIsArticleAlredySaved(wikiArticle.articleId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    if(it.size==0)
                    saveReadWikiArticle(wikiArticle)
                },
                onError = {
                    mErrorLiveData.value=it.message
                }
            )

    }

    fun createWikiArticleList(wikiArticleData: ArrayList<WikiArticleData>):ArrayList<WikiArticle>{
        return wikiArticleData.map { WikiArticle(it.pageId,it.title, it.descriptionData?.description?.get(0),
            it.imageData?.source, it.fullurl) } as ArrayList<WikiArticle>
    }


    fun getSaveWikiArticleLiveData(): LiveData<ArrayList<WikiArticle>> = mSavedWikiArticleLiveData
    fun getErrorLiveData(): LiveData<String> = mErrorLiveData
}