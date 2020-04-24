package com.e.travelpoc.screens

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.e.travelpoc.R
import com.e.travelpoc.models.Employee
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {
    var mContext: Context = DetailsActivity@ this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //Accessing employee object sent from listing with Intent
        var employee = intent.getParcelableExtra<Employee>("key")


        // Setting values to fields
        tvEmpName.text = "Name  - " + employee.employee_name
        tvEmpAge.text = "Age   - " + employee.employee_age.toString()
        tvEmpId.text = "Id    - " + employee.id.toString()
        tvEmpSalary.text = "Salary - " + employee.employee_salary
        //Loading image from web
        var abc: RequestOptions = RequestOptions().placeholder(R.drawable.ic_profile)
        Glide.with(mContext)
            .load(employee.profile_image)
            .apply(abc).into(ivProfileImage)

    }
}
