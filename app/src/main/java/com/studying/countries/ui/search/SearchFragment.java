package com.studying.countries.ui.search;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.studying.countries.R;
import com.studying.countries.databinding.FragmentSearchBinding;
import com.studying.countries.navigate.OnSearchActionListener;

import java.util.HashMap;
import java.util.Map;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding = null;
    private static SearchFragment searchFragment;
    public static final String IN_POPULATION = "SearchFragment.IN_POPULATION";
    public static final String IN_ARIA = "SearchFragment.IN_ARIA";
    public static final String IN_REGION = "SearchFragment.IN_REGION";

    private OnSearchActionListener searchActionListener;

    public static SearchFragment getInstance() {
        if (searchFragment == null) {
            searchFragment = new SearchFragment();
        }
        return searchFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchActionListener) {
            searchActionListener = (OnSearchActionListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);

        binding.searchLine.setOnClickListener(v -> {
            if (binding.searchBar.getVisibility() == View.GONE) {
                binding.searchBar.setVisibility(View.VISIBLE);
                binding.searchLine.setText(R.string.search_close_line_text);
            } else {
                binding.searchBar.setVisibility(View.GONE);
                binding.searchLine.setText(R.string.click_to_search_text);
            }

        });

        binding.searchBut.setOnClickListener(v -> {
            Map<String, String> outData = new HashMap<>();
            outData.put(IN_POPULATION, binding.inPopulation.getText().toString());
            outData.put(IN_ARIA, binding.inAria.getText().toString());
            outData.put(IN_REGION, binding.inRegion.getText().toString());
            searchActionListener.onSearchButtonClick(outData);
            binding.searchBar.setVisibility(View.GONE);
        });
        return binding.getRoot();
    }

    public void backPressed(){
        if (binding != null){
            binding.searchBar.setVisibility(View.GONE);
        }
    }
}
