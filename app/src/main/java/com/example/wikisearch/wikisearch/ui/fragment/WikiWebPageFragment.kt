package com.example.wikisearch.wikisearch.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.wikisearch.databinding.FragmentWikiWebPageBinding
import com.example.wikisearch.wikisearch.BINDEL_KEY_URL


class WikiWebPageFragment : Fragment() {

    private lateinit var mBinding: FragmentWikiWebPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentWikiWebPageBinding.inflate(inflater, container, false)
        arguments?.getString(BINDEL_KEY_URL)?.let {

            mBinding.wikiWebview.setWebViewClient(WebViewClient())
            mBinding.wikiWebview.loadUrl(it) }

        return mBinding.root
    }





}