package com.subashrimal.onlinecourseplatform.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.subashrimal.onlinecourseplatform.R
import com.subashrimal.onlinecourseplatform.model.Course

class CategoriesAdapter(
    val lstCourse : MutableList<Course>,
    val context: Context
) : RecyclerView.Adapter<CategoriesAdapter.categoryViewHolder>() {
    class categoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val type: TextView = view.findViewById(R.id.courseType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): categoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.vertical_design, parent, false)

        return categoryViewHolder(view)
    }


    override fun onBindViewHolder(holder: categoryViewHolder, position: Int) {
        val post = lstCourse[position]
        holder.type.text = post.courseType


    }

    override fun getItemCount(): Int {
        return lstCourse.size
    }
}