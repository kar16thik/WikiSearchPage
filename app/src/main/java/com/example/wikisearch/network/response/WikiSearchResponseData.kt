package com.example.wikisearch.network.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WikiSearchResponseData (@SerializedName("pages")
                                   @Expose
                                   var wikiArticleData: ArrayList<WikiArticleData>)