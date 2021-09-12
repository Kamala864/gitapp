package com.subashrimal.onlinecourseplatform

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.core.app.ActivityCompat
import com.subashrimal.onlinecourseplatform.api.ServiceBuilder
import com.subashrimal.onlinecourseplatform.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var tvsignup: TextView
    private lateinit var username: EditText
    private lateinit var etpassword: EditText
    private lateinit var login: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tvsignup = findViewById(R.id.tvsignup)
        username = findViewById(R.id.username)
        etpassword = findViewById(R.id.etpassword)
        login = findViewById(R.id.login)


        if(!permissionCheck()){
            requestPermission()
        }

        login.setOnClickListener {
            if (TextUtils.isEmpty(username.text)) {
                username.error = "Enter any username"
                username.requestFocus()
                return@setOnClickListener
            } else if (TextUtils.isEmpty(etpassword.text)) {
                etpassword.error = "Enter your password"
                etpassword.requestFocus()
                return@setOnClickListener
            }
            login()
        }

        tvsignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    private fun permissionCheck(): Boolean {
        var hasPermission = true
        for(permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                hasPermission = false
                break
            }
        }
        return hasPermission
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this@LoginActivity, permissions,1)
    }

    private fun login() {
        val username = username.text.toString()
        val password = etpassword.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository()
                val response = repository.loginUser(username, password)

                if (response.success == true) {
                    ServiceBuilder.token = "Bearer ${response.token}"

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "Login success", Toast.LENGTH_SHORT).show()
                    }
                    startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main){
                    Toast.makeText(this@LoginActivity, "Either Username or Password Incorrrect", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}