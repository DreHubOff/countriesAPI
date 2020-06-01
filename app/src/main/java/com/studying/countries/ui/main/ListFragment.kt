package com.studying.countries.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.studying.countries.CountryAdapter
import com.studying.countries.R
import com.studying.countries.network.model.Country
import com.studying.countries.network.model.CountryDatabase
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    companion object {
        private var adapter: CountryAdapter? = null
        private var countryDatabase: CountryDatabase? = null
        private const val EXTRA_LIST = "ListFragment.EXTRA_LIST"
        private var listFragment: ListFragment? = null

        @JvmStatic
        fun getInstance(countryDatabase: CountryDatabase?): ListFragment? {
            if (listFragment == null) listFragment = ListFragment()
            return listFragment?.apply { arguments = Bundle().apply { putParcelable(EXTRA_LIST, countryDatabase) } }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) countryDatabase = arguments?.getParcelable(EXTRA_LIST)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        adapter = CountryAdapter()
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        my_list.adapter = adapter?.apply { update(countryDatabase?.countries as List<Country>?) }
    }

    fun hasNewData(inData: Map<String, String>) = adapter?.filter(inData)
}