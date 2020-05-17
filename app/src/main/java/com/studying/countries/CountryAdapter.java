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

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryHolder> {

    private List<Country> list;

    public CountryAdapter(List<Country> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public CountryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCountryBinding binding = ItemCountryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CountryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryHolder holder, int position) {
        Country country = list.get(position);
        GlideToVectorYou
                .init()
                .with(holder.binding.flag.getContext())
                .load(Uri.parse(country.flag), holder.binding.flag);
        holder.bind(country);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(List<Country> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
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