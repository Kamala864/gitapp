package com.subashrimal.onlinecourseplatform

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.subashrimal.onlinecourseplatform.model.Course
import com.subashrimal.onlinecourseplatform.notification.NotificationChannels
import com.subashrimal.onlinecourseplatform.repository.CourseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity() {
    private lateinit var etxtPname: EditText
    private lateinit var etxtPdec: EditText
    private lateinit var etxtPrice: EditText
    private lateinit var spnProduct: Spinner
    private lateinit var imgProduct: ImageView
    private lateinit var selectedProduct: String
    private lateinit var btnSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        etxtPname = findViewById(R.id.etxtPname)
        etxtPdec = findViewById(R.id.etxtPdec)
        etxtPrice = findViewById(R.id.etxtPrice)
        spnProduct = findViewById(R.id.spnProduct)
        imgProduct = findViewById(R.id.imgProduct)
        btnSave = findViewById(R.id.btnSave)

        val products = arrayOf("Data Science", "Electronics", "IOT", "IT")

        val adapter = ArrayAdapter(
            this!!,
            android.R.layout.simple_list_item_1,
            products
        )
        spnProduct.adapter = adapter

        spnProduct.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    selectedProduct = parent?.getItemAtPosition(position).toString()
                }
            }

        btnSave.setOnClickListener {
            addCourse()
        }

        imgProduct.setOnClickListener {
            loadPopUpMenu()
        }
    }

    private fun loadPopUpMenu() {
        val popMenu = PopupMenu(this@AddCourseActivity, imgProduct)
        popMenu.menuInflater.inflate(R.menu.gallery_camera, popMenu.menu)
        popMenu.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.menuGallery) {
                openGallery()
            } else if (item.itemId == R.id.menuCamera) {
                openCamera()
            }
            true
        }
        popMenu.show()
    }

    private val CAMERACODE = 1
    private val GALLERYCODE = 0
    private var imageUrl = ""

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, GALLERYCODE)
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERACODE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            //gallery
            if (requestCode == GALLERYCODE && data != null) {
                val selectedImage = data.data
                val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                val contentResolver = contentResolver
                val cursor =
                    contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
                cursor!!.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                imageUrl = cursor.getString(columnIndex)
                imgProduct.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
                cursor.close()
            }
        } else if (requestCode == CAMERACODE && data != null) {
            val imageBitmap = data.extras?.get("data") as Bitmap
            val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val file = bitmapToFile(imageBitmap, "$timeStamp.jpg")
            imageUrl = file!!.absolutePath
            imgProduct.setImageBitmap(BitmapFactory.decodeFile(imageUrl))
        }

    }

    private fun bitmapToFile(
        bitmap: Bitmap,
        fileNameToSave: String
    ): File? {
        var file: File? = null
        return try {
            file = File(
                getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                    .toString() + File.separator + fileNameToSave
            )
            file.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
            val bitMapData = bos.toByteArray()
            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitMapData)
            fos.flush()
            fos.close()
            file

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            file // it will return null
        }

    }

    private fun addCourse() {

        val courseTitle = etxtPname.text.toString()
        val courseContent = etxtPdec.text.toString()
        val coursePrice = etxtPrice.text.toString()
        val courseType = selectedProduct

        val course = Course(
            courseTitle = courseTitle,
            courseContent = courseContent,
            coursePrice = coursePrice,
            courseType = courseType
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val studentRepository = CourseRepository()
                val response = studentRepository.addCourse(course)
                if (response.success == true) {
//                    if (imageUrl != "") {
//                        uploadImage(response.data!!._id)
//                    }
                    startActivity(Intent(this@AddCourseActivity,DashboardActivity::class.java))
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@AddCourseActivity,
                            "Course Added",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            } catch (ex: Exception) {
//                withContext(Dispatchers.Main) {
//                    Toast.makeText(
//                        this@AddCourseActivity,
//                        ex.toString(),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
            }
        }
    }

    private fun uploadImage(id: String) {

        val file = File(imageUrl)
        val mimeType = getMimeType(file)

        val reqFile = RequestBody.create(MediaType.parse(mimeType), file)

        val body = MultipartBody.Part.createFormData("file", file.name, reqFile)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val studentRepository = CourseRepository()
                val response = studentRepository.uploadImage(id, body)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@AddCourseActivity,
                            "Upload bhayo hai",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("Mero Error ", ex.localizedMessage)
                    Toast.makeText(
                        this@AddCourseActivity,
                        ex.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
        return type
    }

    private fun showHighPriorityNotification() {

        val notificationManager = NotificationManagerCompat.from(this!!)

        val notificationChannels = NotificationChannels(this!!)
        notificationChannels.createNotificationChannels()

        val notification = NotificationCompat.Builder(this!!, notificationChannels.CHANNEL_1)
            .setSmallIcon(R.drawable.noti)
            .setContentTitle("Product Added")
            .setContentText("New Product is added in $selectedProduct category.")
            .setColor(Color.BLUE)
            .build()

        notificationManager.notify(1, notification)
    }
}