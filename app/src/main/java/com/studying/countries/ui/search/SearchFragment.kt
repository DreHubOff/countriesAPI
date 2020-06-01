package com.studying.countries.ui.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.studying.countries.R
import com.studying.countries.navigate.OnSearchActionListener
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*

class SearchFragment : Fragment() {

    private var searchActionListener: OnSearchActionListener? = null

    companion object {
        private var searchFragment: SearchFragment? = null
        const val IN_POPULATION = "SearchFragment.IN_POPULATION"
        const val IN_ARIA = "SearchFragment.IN_ARIA"
        const val IN_REGION = "SearchFragment.IN_REGION"

        @JvmStatic
        fun getInstance(): SearchFragment? {
            if (searchFragment == null) searchFragment = SearchFragment()
            return searchFragment
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSearchActionListener) {
            searchActionListener = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        search_line.setOnClickListener {
            when (search_bar.visibility) {
                View.GONE -> {
                    search_bar.visibility = View.VISIBLE
                    search_line.setText(R.string.search_close_line_text)
                }
                else -> {
                    search_bar.visibility = View.GONE
                    search_line.setText(R.string.click_to_search_text)
                }
            }
        }

        search_but.setOnClickListener {
            val outData = HashMap<String, String>().apply {
                put(IN_POPULATION, in_population.text.toString())
                put(IN_ARIA, in_aria.text.toString())
                put(IN_REGION, in_region.text.toString())
            }
            searchActionListener?.onSearchButtonClick(outData)
            search_bar.visibility = View.GONE
            search_line.setText(R.string.click_to_search_text)
        }
    }

    fun backPressed() {
        search_bar.visibility = View.GONE
        search_line.setText(R.string.click_to_search_text)
    }


}