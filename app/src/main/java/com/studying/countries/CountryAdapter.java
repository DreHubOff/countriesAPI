package com.studying.countries;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.studying.countries.databinding.ItemCountryBinding;
import com.studying.countries.network.model.Country;
import com.studying.countries.ui.search.SearchFragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryHolder> {

    private List<Country> originalList = new ArrayList<>();

    private List<Country> sortedList = new ArrayList<>();


    @NonNull
    @Override
    public CountryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCountryBinding binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CountryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryHolder holder, int position) {
        Country country = sortedList.get(position);
        GlideToVectorYou
                .init()
                .with(holder.binding.flag.getContext())
                .load(Uri.parse(country.flag), holder.binding.flag);
        holder.bind(country);
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void update(List<Country> list) {
        originalList.clear();
        originalList.addAll(list);

        sortedList.clear();
        sortedList.addAll(list);
        notifyDataSetChanged();
    }

    public void filter(Map<String, String> inData) {
        sortedList.clear();
        sortedList.addAll(originalList);
        if (inData.get(SearchFragment.IN_REGION).isEmpty() &&
                inData.get(SearchFragment.IN_ARIA).isEmpty() &&
                inData.get(SearchFragment.IN_POPULATION).isEmpty()) {
            // todo
        } else {
            if (!inData.get(SearchFragment.IN_REGION).isEmpty()) {
                filterByRegion(inData.get(SearchFragment.IN_REGION));
            }
            if (!inData.get(SearchFragment.IN_ARIA).isEmpty()) {
                filterByAria(inData.get(SearchFragment.IN_ARIA));
            }
            if (!inData.get(SearchFragment.IN_POPULATION).isEmpty()) {
                filterByPopulation(inData.get(SearchFragment.IN_POPULATION));
            }
        }
        notifyDataSetChanged();
    }

    private void filterByPopulation(String inPop) {
        Iterator<Country> iterator = sortedList.iterator();
        while (iterator.hasNext()) {
            Country country = iterator.next();
            double population = Double.parseDouble(country.population);
            double inPopulation = Double.parseDouble(inPop);
            if ((inPopulation - inPopulation * 0.15) > population ||
                    (inPopulation + inPopulation * 0.15) < population) {
                iterator.remove();
            }
        }
    }

    private void filterByAria(String inAria) {
        Iterator<Country> iterator = sortedList.iterator();
        while (iterator.hasNext()) {
            Country country = iterator.next();
            double aria;
            try {
                aria = Double.parseDouble(country.area.replaceAll("\\.0", ""));
            } catch (Exception ignored) {
                continue;
                // довольно долго мучался с этим моментом. Пока так залепил. Часто эксепшины выскакивали.
            }
            double inAriaDoub = Double.parseDouble(inAria);
            if ((inAriaDoub - inAriaDoub * 0.15) > aria ||
                    (inAriaDoub + inAriaDoub * 0.15) < aria) {
                iterator.remove();
            }
        }
    }

    private void filterByRegion(String inReg) {
        Iterator<Country> iterator = sortedList.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().region.toLowerCase().startsWith(inReg.toLowerCase())) {
                iterator.remove();
            }
        }
    }


    static class CountryHolder extends RecyclerView.ViewHolder {
        private ItemCountryBinding binding;

        public CountryHolder(ItemCountryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("DefaultLocale")
        public void bind(Country country) {
            binding.countryNameItem.setText(country.name);
            binding.capitalItem.setText(country.capital);
            binding.populationItem.setText(String.format("%,d", Integer.parseInt(country.population)));
            if (country.area != null) {
                try {
                    binding.ariaItem.setText(String.format("%,d", Integer.parseInt(country.area.replaceAll("\\.0", ""))));
                } catch (Exception ignored) {
                    // довольно долго мучался с этим моментом. Пока так залепил.
                }
            }
            binding.regionItem.setText(country.region);
            binding.currenciesItem.setText(country.getCurrencies());
            binding.collingCodesItem.setText(country.getCollingCodes());

            binding.infoItem.setVisibility(View.GONE);

            binding.itemRoot.setOnClickListener(v -> {
                if (binding.infoItem.getVisibility() == View.GONE) {
                    binding.infoItem.setVisibility(View.VISIBLE);
                } else {
                    binding.infoItem.setVisibility(View.GONE);
                }
            });

        }
    }


}
