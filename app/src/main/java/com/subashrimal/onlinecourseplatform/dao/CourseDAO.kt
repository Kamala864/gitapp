package com.subashrimal.onlinecourseplatform.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.subashrimal.onlinecourseplatform.model.Course


@Dao
interface CourseDAO {
    @Insert
    suspend fun insertCourse(course: Course)

    @Query("SELECT * FROM Course")
    suspend fun getAllCourse(): MutableList<Course>

    @Query("DELETE FROM Course")
    suspend fun deleteAll()
}