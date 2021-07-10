package com.example.wikisearch.wikisearch.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wikisearch.R
import com.example.wikisearch.databinding.FragmentWikiSavedListBinding
import com.example.wikisearch.databinding.FragmentWikiSearchBinding
import com.example.wikisearch.wikisearch.BINDEL_KEY_URL
import com.example.wikisearch.wikisearch.ui.adapter.WikiResultListAdapter
import com.example.wikisearch.wikisearch.viewmodel.WikiSearchViewModel
import org.koin.android.ext.android.inject

class WikiSavedListFragment : Fragment() {

    private lateinit var mBinding: FragmentWikiSavedListBinding
    private val mWikiSearchViewModel: WikiSearchViewModel by inject()
    private lateinit var mWikiResultListAdapter: WikiResultListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentWikiSavedListBinding.inflate(inflater, container, false)

        return mBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        getDataStreem()
        mWikiSearchViewModel.getSavedArticleData()

    }

    private fun initUi() {
        mBinding.wikiSearch.setOnClickListener {
            loadFragment(WikiSearchFragment(), null,"search_fragment" )
        }
        initResultAdapter()

    }

    private fun initResultAdapter(){
        mWikiResultListAdapter= WikiResultListAdapter(onItemSelect={
            val bundle=Bundle()
            bundle.putString(BINDEL_KEY_URL,it.fullArticleUrl)
            loadFragment(WikiWebPageFragment(), bundle,"web_fragment" )
        })
        mBinding.rvArtical.layoutManager = LinearLayoutManager(activity)
        mBinding.rvArtical.adapter=mWikiResultListAdapter

    }
    private fun getDataStreem(){
        mWikiSearchViewModel.getSaveWikiArticleLiveData().observe(viewLifecycleOwner, Observer {

            it?.let {
                if(it.size>0) {
                    mWikiResultListAdapter.updateArticleData(it)
                    mBinding.noData.visibility=View.GONE
                }
                else noDataFound()
            }

        })
        mWikiSearchViewModel.getErrorLiveData().observe(viewLifecycleOwner, Observer {

            it?.let {
               Toast.makeText(activity,it,Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun noDataFound(){
        mBinding.noData.visibility=View.VISIBLE
    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment, bundle: Bundle?,fragmentName:String ) {
        fragment.arguments = bundle
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.let {
            it.replace(R.id.fragment_container, fragment, fragmentName)
            it.addToBackStack(fragmentName)
            it.commit()
        }
    }


}