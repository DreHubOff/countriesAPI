package com.studying.countries

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.studying.countries.CountryAdapter.CountryHolder
import com.studying.countries.databinding.ItemCountryBinding
import com.studying.countries.network.model.Country
import com.studying.countries.ui.search.SearchFragment
import java.util.*

class CountryAdapter : RecyclerView.Adapter<CountryHolder>() {
    private val originalList: MutableList<Country> = ArrayList()
    private val sortedList: MutableList<Country> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
        val binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountryHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        val country = sortedList[position]
        GlideToVectorYou
                .init()
                .with(holder.binding.flag.context)
                .load(Uri.parse(country.flag), holder.binding.flag)
        holder.bind(country)
    }

    override fun getItemCount() = sortedList.size

    fun update(list: List<Country>?) {
        if (list != null) {
            originalList.apply {
                clear()
                addAll(list)
            }
            sortedList.addAll(list)
        }
        notifyDataSetChanged()
    }

    fun filter(inData: Map<String, String>) {
        sortedList.apply {
            clear()
            addAll(originalList)
        }
        if (inData.getOrDefault(SearchFragment.IN_REGION, "").isNotEmpty())
            filterByRegion(inData[SearchFragment.IN_REGION])
        if (inData.getOrDefault(SearchFragment.IN_ARIA, "").isNotEmpty())
            filterByAria(inData[SearchFragment.IN_ARIA])
        if (inData.getOrDefault(SearchFragment.IN_POPULATION, "").isNotEmpty())
            filterByPopulation(inData[SearchFragment.IN_POPULATION].orEmpty().ifEmpty { "0" })
        notifyDataSetChanged()
    }

    private fun filterByPopulation(inPop: String) {
        sortedList.removeIf {
            it.population.orEmpty().ifEmpty { "0" }.toDouble() !in
                    ((inPop?.toDouble() - inPop?.toDouble() * 0.15)..(inPop?.toDouble() + inPop?.toDouble() * 0.15))
        }
    }

    private fun filterByAria(inAria: String?) {
        val iterator = sortedList.iterator()
        while (iterator.hasNext()) {
            val country = iterator.next()
            var aria: Double
            aria = try {
                country.area.orEmpty().ifEmpty { "0" }.replace("\\.0".toRegex(), "").toDouble()
            } catch (ignored: Exception) {
                continue
            }
            val inAriaDoub = inAria!!.toDouble()
            if (inAriaDoub - inAriaDoub * 0.15 > aria ||
                    inAriaDoub + inAriaDoub * 0.15 < aria) {
                iterator.remove()
            }
        }
    }

    private fun filterByRegion(inReg: String?) {
        val iterator = sortedList.iterator()
        while (iterator.hasNext()) {
            if (!iterator.next().region.orEmpty().ifEmpty { "" }.toLowerCase().startsWith(inReg!!.toLowerCase())) {
                iterator.remove()
            }
        }
    }

    class CountryHolder(val binding: ItemCountryBinding) : ViewHolder(binding.root) {
        @SuppressLint("DefaultLocale")
        fun bind(country: Country) {
            binding.countryNameItem.text = country.name
            binding.capitalItem.text = country.capital
            binding.populationItem.text = String.format("%,d", country.population.orEmpty().ifEmpty { "0" }.toInt())
            if (country.area != null) {
                try {
                    binding.ariaItem.text = String.format("%,d", country.area.orEmpty().ifEmpty { "0" }.replace("\\.0".toRegex(), "").toInt())
                } catch (ignored: Exception) {
                }
            }
            binding.regionItem.text = country.region
            binding.currenciesItem.text = country.getCurrencies()
            binding.collingCodesItem.text = country.collingCodes
            binding.infoItem.visibility = View.GONE
            binding.itemRoot.setOnClickListener {
                if (binding.infoItem.visibility == View.GONE) {
                    binding.infoItem.visibility = View.VISIBLE
                } else {
                    binding.infoItem.visibility = View.GONE
                }
            }
        }

    }
}