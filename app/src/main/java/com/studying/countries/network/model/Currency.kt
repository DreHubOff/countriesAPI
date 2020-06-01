package com.studying.countries.network.model

import android.os.Parcel
import android.os.Parcelable

class Currency protected constructor(`in`: Parcel) : Parcelable {
    var code: String?
    var name: String?
    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(code)
        dest.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<Currency?> = object : Parcelable.Creator<Currency?> {
            override fun createFromParcel(`in`: Parcel): Currency? {
                return Currency(`in`)
            }

            override fun newArray(size: Int): Array<Currency?> {
                return arrayOfNulls(size)
            }
        }
    }

    init {
        code = `in`.readString()
        name = `in`.readString()
    }
}