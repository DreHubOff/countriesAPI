package com.studying.countries.network.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class CountryDatabase : Parcelable {
    var countries: List<Country?>
        private set

    constructor(countries: List<Country?>) {
        this.countries = countries
    }

    private constructor(`in`: Parcel) {
        countries = (`in`.createTypedArrayList(Country.CREATOR))?.toList()!!
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeTypedList(countries)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<CountryDatabase?> = object : Parcelable.Creator<CountryDatabase?> {
            override fun createFromParcel(`in`: Parcel): CountryDatabase? {
                return CountryDatabase(`in`)
            }

            override fun newArray(size: Int): Array<CountryDatabase?> {
                return arrayOfNulls(size)
            }
        }
    }
}