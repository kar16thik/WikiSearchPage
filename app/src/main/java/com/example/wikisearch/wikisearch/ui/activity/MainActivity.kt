package com.example.wikisearch.wikisearch.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.wikisearch.R
import com.example.wikisearch.databinding.ActivityMainBinding
import com.example.wikisearch.wikisearch.ui.fragment.WikiSavedListFragment
import com.example.wikisearch.wikisearch.ui.fragment.WikiSearchFragment
import com.example.wikisearch.wikisearch.viewmodel.WikiSearchViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mBinding.root)
        loadFragment(WikiSavedListFragment(), "save_fragment")
    }

    private fun loadFragment(fragment: Fragment, tag: String) {

        supportFragmentManager.beginTransaction().replace(
            mBinding.fragmentContainer.id, fragment, tag
        ).commit()

    }
}