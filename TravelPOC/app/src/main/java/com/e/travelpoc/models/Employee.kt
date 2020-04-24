package com.e.travelpoc.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class Employee() : Parcelable, Comparable<Employee> {
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("employee_name")
    var employee_name: String? = null
    @SerializedName("employee_salary")
    var employee_salary: String? = null
    @SerializedName("employee_age")
    var employee_age: Int? = null
    @SerializedName("profile_image")
    var profile_image: String? = null

    override fun compareTo(person: Employee): Int {
        return person.employee_name?.let { employee_name?.compareTo(it) }!!
    }

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        employee_name = parcel.readString()
        employee_salary = parcel.readString()
        employee_age = parcel.readValue(Int::class.java.classLoader) as? Int
        profile_image = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(employee_name)
        parcel.writeString(employee_salary)
        parcel.writeValue(employee_age)
        parcel.writeString(profile_image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Employee> {
        override fun createFromParcel(parcel: Parcel): Employee {
            return Employee(parcel)
        }

        override fun newArray(size: Int): Array<Employee?> {
            return arrayOfNulls(size)
        }
    }
}