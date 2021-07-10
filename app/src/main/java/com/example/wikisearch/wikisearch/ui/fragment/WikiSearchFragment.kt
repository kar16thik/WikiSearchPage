package com.example.wikisearch.wikisearch.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wikisearch.R
import com.example.wikisearch.databinding.FragmentWikiSearchBinding
import com.example.wikisearch.wikisearch.BINDEL_KEY_URL
import com.example.wikisearch.wikisearch.ui.adapter.WikiResultListAdapter
import com.example.wikisearch.wikisearch.viewmodel.WikiSearchViewModel
import org.koin.android.ext.android.inject


class WikiSearchFragment : Fragment() {
    private lateinit var mBinding: FragmentWikiSearchBinding
    private val mWikiSearchViewModel: WikiSearchViewModel by inject()
    private lateinit var mWikiResultListAdapter: WikiResultListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentWikiSearchBinding.inflate(inflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        getDataStreem()

    }

    override fun onResume() {
        super.onResume()
        // this code for auto open keyboard when fragment load
        if(mBinding.wikiSearch.requestFocus()){
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    private fun initUi(){
        initResultAdapter()
        mBinding.wikiSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.isNotEmpty() && newText.length>3) {
                    // this code will search for wiki page after 4 character 
                    mWikiSearchViewModel.getWikiSearchData(newText)
                }else{
                    mWikiResultListAdapter.clearData()
                }
                return false
            }

        })

    }

    private fun initResultAdapter(){
        mWikiResultListAdapter= WikiResultListAdapter(onItemSelect={

            val bundle=Bundle()
            bundle.putString(BINDEL_KEY_URL,it.fullArticleUrl)
            loadFragment(WikiWebPageFragment(), bundle,"web_fragment" )
            mWikiSearchViewModel.checkArticleSaved(it)

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
               else {mWikiResultListAdapter.clearData()
               mBinding.noData.visibility=View.VISIBLE}
           }

        })
        mWikiSearchViewModel.getErrorLiveData().observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(activity,it, Toast.LENGTH_SHORT).show()
            }
        })
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