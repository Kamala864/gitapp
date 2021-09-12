package com.subashrimal.onlinecourseplatform.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.subashrimal.onlinecourseplatform.R
import com.subashrimal.onlinecourseplatform.SingleCourseActivity
import com.subashrimal.onlinecourseplatform.api.ServiceBuilder
import com.subashrimal.onlinecourseplatform.model.Course


class CourseAdapter (
    val lstCourse : MutableList<Course>,
    val context: Context
) : RecyclerView.Adapter<CourseAdapter.ProductViewHolder>(){

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view){
//        val imgProduct : ImageView
        val tvName : TextView = view.findViewById(R.id.tvName)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
        val btnView: ImageView = view.findViewById(R.id.btnView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_layout,parent,false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val course = lstCourse[position]
        holder.tvName.text = course.courseTitle
        holder.tvPrice.text = course.coursePrice

        val imagePath = ServiceBuilder.loadImagePath() + course.courseContent
//        if(!course.courseContent.equals("upload.jpg")) {
//            Glide.with(context)
//                    .load(imagePath)
//                    .fitCenter()
//                    .into(holder.imgProduct)
//        }

        holder.btnView.setOnClickListener {
            val intent = Intent(context, SingleCourseActivity::class.java)
                    .putExtra("course", course)
            startActivity(context,intent,null)

        }
    }
    override fun getItemCount(): Int {
        return lstCourse.size
    }
}