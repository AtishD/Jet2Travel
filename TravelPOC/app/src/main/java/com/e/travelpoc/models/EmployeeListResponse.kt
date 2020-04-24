package com.e.travelpoc.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName



class EmployeeListResponse() :Parcelable {
    @SerializedName("status")
    val status: String? = null
    @SerializedName("data")
    val data: List<Employee>? = null

    constructor(parcel: Parcel) : this() {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EmployeeListResponse> {
        override fun createFromParcel(parcel: Parcel): EmployeeListResponse {
            return EmployeeListResponse(parcel)
        }

        override fun newArray(size: Int): Array<EmployeeListResponse?> {
            return arrayOfNulls(size)
        }
    }
}