package com.e.travelpoc.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.e.travelpoc.R
import com.e.travelpoc.models.Employee

class EmployeeAdapter @Inject constructor(val context: Context, val employeeList: List<Employee>?) :
    RecyclerView.Adapter<EmployeeAdapter.ViewHolder>() {
    var onItemClick: ((Employee) -> Unit)? = null
    var onDeleteClick: ((index: Int) -> Unit)? = null

    annotation class Inject

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_emplist, parent, false)
        return ViewHolder(v)
    }


    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(employeeList!!.get(position))
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return employeeList!!.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            //setting a callback for activity upon list item is clicked
            itemView.setOnClickListener {
                onItemClick?.invoke(employeeList!!.get(adapterPosition))
            }

        }

        fun bindItems(employee: Employee) {
            var ivDelete = itemView.findViewById(R.id.ivDelete) as ImageView
            val tvName = itemView.findViewById(R.id.tvEmpName) as TextView
            val ivProfileImage = itemView.findViewById(R.id.ivProfileImage) as ImageView
            val tvEmpAge = itemView.findViewById(R.id.tvEmpAge) as TextView

            //setting a callback for activity upon delete clicked for item
            ivDelete.setOnClickListener {
                onDeleteClick?.invoke(adapterPosition)
            }
            tvName.text = employee.employee_name
            tvEmpAge.text = (employee.employee_age).toString() + " Years"
            var abc: RequestOptions = RequestOptions().placeholder(R.drawable.ic_profile)
            Glide.with(context)
                .load(employee.profile_image)
                .apply(abc).into(ivProfileImage)


        }
    }


}
