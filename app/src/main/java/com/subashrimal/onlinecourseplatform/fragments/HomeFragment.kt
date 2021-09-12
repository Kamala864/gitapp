package com.subashrimal.onlinecourseplatform.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.subashrimal.onlinecourseplatform.R
import com.subashrimal.onlinecourseplatform.adapter.CategoriesAdapter
import com.subashrimal.onlinecourseplatform.adapter.CourseAdapter
import com.subashrimal.onlinecourseplatform.db.CourseDB
import com.subashrimal.onlinecourseplatform.model.Course
import com.subashrimal.onlinecourseplatform.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {

    private lateinit var bottomRecycler: RecyclerView
    private lateinit var topRecycler : RecyclerView
    private var products = mutableListOf<Course>()
    private lateinit var imgIcon : ImageView
    private lateinit var swipe : SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        topRecycler = view.findViewById(R.id.topRecycler)
        bottomRecycler = view.findViewById(R.id.bottomRecycler)
        imgIcon = view.findViewById(R.id.imgIcon)
        swipe = view.findViewById(R.id.swipe)


        runBlocking {
            deleteProducts().collect()
            loadProduct().collect()
            getProduct().collect{value -> products = value.toMutableList().asReversed() }
        }

        val adapter = CourseAdapter(products, requireContext())
        topRecycler.adapter = adapter
        topRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val adapter2 = CategoriesAdapter(products, requireContext())
        bottomRecycler.adapter = adapter2
        bottomRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return view
    }

    private fun deleteProducts(): Flow<String> = flow{
        CourseDB.getInstance(requireContext()).getCourseDAO().deleteAll()
        emit("Done")
    }

    private fun loadProduct(): Flow<String> = flow{
        val productRepository = CourseRepository()
        val response = productRepository.getAllCourse()
        response.data!!.forEach {
            CourseDB.getInstance(requireContext()).getCourseDAO().insertCourse(it)
            emit("Done")
        }
    }

    private fun getProduct() : Flow<List<Course>> = flow {
        val listProducts = CourseDB.getInstance(requireContext()).getCourseDAO().getAllCourse()
        emit(listProducts)
    }
}