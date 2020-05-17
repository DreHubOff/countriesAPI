package com.studying.countries.network.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Country implements Parcelable {
    public String name;
    public List<String> callingCodes;
    public String capital;
    public String region;
    public String population;
    public String area;
    public List<Currency> currencies;
    public String flag;


    protected Country(Parcel in) {
        name = in.readString();
        callingCodes = in.createStringArrayList();
        capital = in.readString();
        region = in.readString();
        population = in.readString();
        area = in.readString();
        currencies = in.createTypedArrayList(Currency.CREATOR);
        flag = in.readString();
    }

    public static final Creator<Country> CREATOR = new Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeStringList(callingCodes);
        dest.writeString(capital);
        dest.writeString(region);
        dest.writeString(population);
        dest.writeString(area);
        dest.writeTypedList(currencies);
        dest.writeString(flag);
    }

    public String getCurrencies() {
        if (currencies.size() == 1) {
            return currencies.get(0).name + '(' + currencies.get(0).code + ")";
        } else if (currencies.size() > 1) {
            StringBuilder builder = new StringBuilder();
            for (Currency currency : currencies) {
                builder.append(currency.name).append('(').append(currency.code).append(") ");
            }
            return builder.toString();
        }
        return "Non";
    }

    public String getCollingCodes(){
        if (callingCodes.size()>0){
            StringBuilder builder = new StringBuilder();
            for (String code : callingCodes) {
                builder.append('+').append(code).append(" ");
            }
            return builder.toString();
        }
        return "Non";
    }
}
