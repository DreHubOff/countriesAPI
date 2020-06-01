package com.studying.countries.network.model

import android.os.Parcel
import android.os.Parcelable

class Country protected constructor(`in`: Parcel) : Parcelable {
    var name: String?
    var callingCodes: List<String>?
    var capital: String?
    var region: String?
    var population: String?
    var area: String?
    var currencies: List<Currency?>
    var flag: String?
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(name)
        dest.writeStringList(callingCodes)
        dest.writeString(capital)
        dest.writeString(region)
        dest.writeString(population)
        dest.writeString(area)
        dest.writeTypedList(currencies)
        dest.writeString(flag)
    }

    fun getCurrencies(): String {
        if (currencies!!.size == 1) {
            return currencies!![0]?.name + '(' + currencies!![0]?.code + ")"
        } else if (currencies!!.size > 1) {
            val builder = StringBuilder()
            for (currency in currencies!!) {
                builder.append(currency?.name).append('(').append(currency?.code).append(") ")
            }
            return builder.toString()
        }
        return "Non"
    }

    val collingCodes: String
        get() {
            if (callingCodes!!.size > 0) {
                val builder = StringBuilder()
                for (code in callingCodes!!) {
                    builder.append('+').append(code).append(" ")
                }
                return builder.toString()
            }
            return "Non"
        }

    companion object {
        val CREATOR: Parcelable.Creator<Country?> = object : Parcelable.Creator<Country?> {
            override fun createFromParcel(`in`: Parcel): Country? {
                return Country(`in`)
            }

            override fun newArray(size: Int): Array<Country?> {
                return arrayOfNulls(size)
            }
        }
    }

    init {
        name = `in`.readString()
        callingCodes = `in`.createStringArrayList()
        capital = `in`.readString()
        region = `in`.readString()
        population = `in`.readString()
        area = `in`.readString()
        currencies = (`in`.createTypedArrayList(Currency.CREATOR))!!.toList()
        flag = `in`.readString()
    }
}