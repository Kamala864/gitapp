package com.subashrimal.onlinecourseplatform.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.subashrimal.onlinecourseplatform.R
import com.subashrimal.onlinecourseplatform.model.Highlight

class HighlightAdapter(
    val lstCourse: ArrayList<Highlight>,
    val context: Context
) : RecyclerView.Adapter<HighlightAdapter.highlightViewHolder>() {
    class highlightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val courseTitle: TextView = view.findViewById(R.id.courseTitle)
        val coursePrice: TextView = view.findViewById(R.id.coursePrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): highlightViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.horizontal_design, parent, false)

        return highlightViewHolder(view)
    }

    override fun onBindViewHolder(holder: highlightViewHolder, position: Int) {
        val post = lstCourse[position]
        holder.courseTitle.text = post.courseTopic
        holder.coursePrice.text = post.coursePrice
    }

    override fun getItemCount(): Int {
        return lstCourse.size
    }
}