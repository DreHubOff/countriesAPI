package com.studying.countries.ui.main;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.studying.countries.R;
import com.studying.countries.databinding.ActivityMainBinding;
import com.studying.countries.navigate.OnSearchActionListener;
import com.studying.countries.network.ApiService;
import com.studying.countries.network.model.Country;
import com.studying.countries.network.model.CountryDatabase;
import com.studying.countries.ui.search.SearchFragment;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements OnSearchActionListener {
    private ActivityMainBinding binding;

    private Disposable disposable;
    private long lastTimeBack = 0L;
    private Toast toastBackBut;
    private CountryDatabase database = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toastBackBut = Toast.makeText(this, "Click again to exit", Toast.LENGTH_SHORT);

        disposable = ApiService.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showInfo, this::showError);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root_for_search, SearchFragment.getInstance())
                .commit();
    }

    public void showInfo(List<Country> countries) {
        database = new CountryDatabase(countries);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root, ListFragment.getInstance(database))
                .commit();
    }

    public void showError(Throwable t) {
        Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSearchButtonClick(Map<String, String> inData) {
        if (database != null) {
            ListFragment.getInstance(database).hasNewData(inData);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTimeBack > 3000) {
            SearchFragment.getInstance().backPressed();
            toastBackBut.show();
            lastTimeBack = System.currentTimeMillis();
        } else {
            toastBackBut.cancel();
            super.onBackPressed();
        }
    }
}
