package com.e.travelpoc.screens

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.e.travelpoc.R
import com.e.travelpoc.adapters.EmployeeAdapter
import com.e.travelpoc.models.Employee
import com.e.travelpoc.models.EmployeeListResponse
import com.e.travelpoc.network.ApiController
import com.e.travelpoc.network.ApiInterface
import com.e.travelpoc.utils.SharedPreference
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    var mContext: Context = this@MainActivity
    var sharedPreference: SharedPreference? = null
    var mEmployeeList: List<Employee> = emptyList()
    var adapter: EmployeeAdapter? = null

    /**
     * <b>fun onClick(view: View)</b>
     * <br>
     * <p> This function is used to access the all the clicks on this screen</P>
     */
    fun onClick(view: View) {
        when (view) {
            rlRetry ->
                loadEmployeeList()
            rlSortAge -> {
                setData(mEmployeeList!!.sortedWith(compareBy({ it.employee_age })))
            }
            rlSortName -> {
                setData(mEmployeeList!!.sortedWith(compareBy({ it.employee_name })))
            }
            else ->
                Toast.makeText(mContext, "in Progress", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreference = SharedPreference(mContext)
        loadEmployeeList()
    }


    /**
     * <b>private fun loadEmployeeList() </b>
     * <br>
     * <p> This function is used to ;load employee list on screen either from web or local incase internet not available</P>
     */
    private fun loadEmployeeList() {
        tvNoResult.visibility = View.GONE
        rvList.visibility = View.GONE

        if (isOnline()) {
            checkEmployeeList()
        } else if (sharedPreference != null && sharedPreference?.getValueString("emplist")!!.length > 0) {
            var prefs = sharedPreference?.getValueString("emplist")

            val listEmpType = object : TypeToken<List<Employee>>() {}.type
            val empList: List<Employee> = Gson().fromJson(prefs, listEmpType)
            rvList.visibility = View.VISIBLE
            setData(empList)

        } else {
            tvNoResult.visibility = View.VISIBLE
        }


    }

    /**
     * <b>private fun checkEmployeeList()</b>
     * <br>
     * <p> This function is used to get response from web</P>
     */
    private fun checkEmployeeList() {
        progressBar.visibility = View.VISIBLE
        //seting up client of Retrofit to call webservice/API
        var apiService: ApiInterface = ApiController.getClient()!!.create(
            ApiInterface::class.java
        )
        //Requesting data from a function @ Base url using retrofit
        val call: Call<EmployeeListResponse> = apiService.getEmployeeList()
        call.enqueue(object : Callback<EmployeeListResponse> {
            override fun onResponse(
                call: Call<EmployeeListResponse>?,
                response: Response<EmployeeListResponse>?
            ) {
                progressBar.visibility = View.GONE
                rvList.visibility = View.VISIBLE

                var resp: EmployeeListResponse = response!!.body()
                mEmployeeList = resp.data!!
                val gs = Gson()
                val itemListJsonString = gs.toJson(mEmployeeList)
                setData(mEmployeeList)
                sharedPreference?.save("emplist", itemListJsonString)
                //now adding the adapter to recyclerview
            }

            override fun onFailure(call: Call<EmployeeListResponse>?, t: Throwable?) {
                progressBar.visibility = View.GONE
                tvNoResult.visibility = View.VISIBLE

                Log.e("", t.toString())
            }
        })
    }

    /**
     * <b>private fun setData(empList: List<Employee>)</b>
     * <br>
     * <p> This function is used to set list of employees to recyclerview</P>
     */
    private fun setData(empList: List<Employee>) {
        mEmployeeList = empList
        adapter = EmployeeAdapter(mContext, empList)
        rvList.adapter = adapter

        //This is callback from adpater when list item is clicked
        adapter!!.onItemClick = { employee ->
            //Redirecting to details activity with information of clicked employee
            val intent = Intent(mContext, DetailsActivity::class.java)
            intent.putExtra("key", employee)
            startActivity(intent)
        }
        //This is callback from adpater when delete for any list item is clicked
        adapter!!.onDeleteClick = { index ->
            var temp = mEmployeeList.toMutableList()
            temp.removeAt(index)
            setData(temp)
        }
    }

    /**
     * <b>fun isOnline(): Boolean</b>
     * <br>
     * <p> This function is used to check if device is connected to internet by all means</P>
     */
    fun isOnline(): Boolean {
        val connectivityManager =
            mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

}
