package com.studying.countries.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.studying.countries.CountryAdapter;
import com.studying.countries.databinding.FragmentListBinding;
import com.studying.countries.network.model.CountryDatabase;

public class ListFragment extends Fragment {
    private static final String EXTRA_LIST = "ListFragment.EXTRA_LIST";

    private static ListFragment listFragment;
    private CountryDatabase countryDatabase;

    private FragmentListBinding binding;

    private CountryAdapter adapter;

    public static ListFragment getInstance(CountryDatabase countryDatabase) {
        if (listFragment == null) {
            listFragment = new ListFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_LIST, countryDatabase);
        listFragment.setArguments(bundle);
        return listFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            countryDatabase = getArguments().getParcelable(EXTRA_LIST);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        adapter = new CountryAdapter();
        adapter.update(countryDatabase.getCountries());
        binding.myList.setAdapter(adapter);

        binding.searchBut.setOnClickListener(this::onSearchButtonClick);

        return binding.getRoot();
    }

    public void onSearchButtonClick(View view) {
        final String inString = binding.inName.getText().toString();
        adapter.filterByName(inString);
    }


}
