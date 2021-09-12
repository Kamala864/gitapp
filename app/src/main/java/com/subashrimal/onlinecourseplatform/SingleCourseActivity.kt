package com.subashrimal.onlinecourseplatform


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.subashrimal.onlinecourseplatform.api.ServiceBuilder
import com.subashrimal.onlinecourseplatform.model.Course
import com.subashrimal.onlinecourseplatform.notification.NotificationChannels


class SingleCourseActivity : AppCompatActivity() {

    private lateinit var imgProduct: ImageView
    private lateinit var txtPname: TextView
    private lateinit var txtCategory: TextView
    private lateinit var txtPrice: TextView
    private lateinit var txtDes: TextView
    private lateinit var etxtQty: TextView
    private lateinit var txtQty: TextView
    private lateinit var btnCart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_course)

        imgProduct = findViewById(R.id.imgProduct)
        txtPname = findViewById(R.id.txtPname)
        txtCategory = findViewById(R.id.txtCategory)
        txtPrice = findViewById(R.id.txtPrice)
        txtDes = findViewById(R.id.txtDes)
        txtQty = findViewById(R.id.txtQty)
        etxtQty = findViewById(R.id.etxtQty)
        btnCart = findViewById(R.id.btnCart)

        val product = intent.getParcelableExtra<Course>("course")!!
        txtPname.setText(product.courseTitle)
        txtCategory.setText(product.courseType)
        txtPrice.setText("Rs." + product.coursePrice)
        txtDes.setText(product.courseContent)
        etxtQty.setText("1")

        val image = ServiceBuilder.loadImagePath() + product.courseAuthor

        Glide.with(this)
            .load(image)
            .fitCenter()
            .into(imgProduct)

//        if(ServiceBuilder.accountType=="Tutor"){
//            etxtQty.visibility = GONE
//            btnCart.visibility = GONE
//            txtQty.visibility = GONE
//        }

//        btnCart.setOnClickListener {
//            val quantity = etxtQty.text.toString()
//
//            val cart = Cart(quantity = quantity)
//
//            if(ServiceBuilder.accountType=="Buyer") {
//
//                CoroutineScope(Dispatchers.IO).launch {
//                    try {
//                        val cartRepository = CartRepository()
//                        val response = cartRepository.addtoCart(product._id!!, cart)
//                        if (response.success == true) {
//                            withContext(Main) {
//                                showLowPriorityNotification()
//                                Toast.makeText(this@SingleProductActivity, "Product Added to Cart", Toast.LENGTH_SHORT).show()
//                                val intent = Intent(this@SingleProductActivity, DashboardActivity::class.java)
//                                startActivity(intent)
//                            }
//                        }
//
//                    } catch (ex: Exception) {
//
//                    }
//                }
//            }
//        }
    }

    private fun showLowPriorityNotification() {
        val notificationManager = NotificationManagerCompat.from(this)

        val notificationChannels = NotificationChannels(this)
        notificationChannels.createNotificationChannels()

        val notification = NotificationCompat.Builder(this, notificationChannels.CHANNEL_2)
            .setSmallIcon(R.drawable.noti)
            .setContentTitle("Product Added to Cart")
            .setContentText("Product is added to Cart.")
            .setColor(Color.BLUE)
            .build()

        notificationManager.notify(2, notification)
    }
}