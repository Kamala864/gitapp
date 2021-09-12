package com.subashrimal.onlinecourseplatform.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.subashrimal.onlinecourseplatform.dao.CourseDAO
import com.subashrimal.onlinecourseplatform.model.Course


@Database(
    entities = [(Course::class)],
    version = 2,
    exportSchema = false

)

abstract class CourseDB: RoomDatabase() {

    abstract fun getCourseDAO(): CourseDAO

    companion object{
        @Volatile
        private var instance: CourseDB? = null

        fun getInstance(context: Context): CourseDB{
            if (instance == null){
                synchronized(CourseDB::class){
                    instance = createDatabase(context)
                }
            }
            return instance!!
        }

        private fun createDatabase(context: Context):CourseDB {
            return Room.databaseBuilder(
                context.applicationContext,
                CourseDB::class.java,
                "CourseDB"
            ).build()
        }
    }
}