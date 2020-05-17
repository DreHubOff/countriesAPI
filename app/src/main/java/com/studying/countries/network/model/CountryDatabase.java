package com.studying.countries.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CountryDatabase implements Parcelable {
    private List<Country> countries;

    public CountryDatabase(List<Country> countries) {
        this.countries = countries;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public List<Country> getSortCountries(String inString){
        List<Country> outList = new ArrayList<>(countries);
        Iterator<Country> iterator = outList.iterator();
        while (iterator.hasNext()){
            if (!iterator.next().name.toLowerCase().startsWith(inString.toLowerCase())){
             iterator.remove();
            }
        }
        return outList;
    }

    protected CountryDatabase(Parcel in) {
        countries = in.createTypedArrayList(Country.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(countries);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CountryDatabase> CREATOR = new Creator<CountryDatabase>() {
        @Override
        public CountryDatabase createFromParcel(Parcel in) {
            return new CountryDatabase(in);
        }

        @Override
        public CountryDatabase[] newArray(int size) {
            return new CountryDatabase[size];
        }
    };

}
