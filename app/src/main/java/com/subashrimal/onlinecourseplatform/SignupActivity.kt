package com.subashrimal.onlinecourseplatform

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.subashrimal.onlinecourseplatform.model.User
import com.subashrimal.onlinecourseplatform.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupActivity : AppCompatActivity() {
    private lateinit var tvsignin: TextView
    private lateinit var etusername: EditText
    private lateinit var etemail: EditText
    private lateinit var etpassword: EditText
    private lateinit var etconpassword: EditText
    private lateinit var btnsignup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        tvsignin = findViewById(R.id.tvsignin)
        etusername = findViewById(R.id.etusername)
        etemail = findViewById(R.id.etemail)
        etpassword = findViewById(R.id.etpassword)
        etconpassword = findViewById(R.id.etconpassword)
        btnsignup = findViewById(R.id.btnsignup)

        tvsignin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        btnsignup.setOnClickListener {

            if (TextUtils.isEmpty(etusername.text)) {
                etusername.error = "Enter any username"
                etusername.requestFocus()
                return@setOnClickListener
            } else if (TextUtils.isEmpty(etemail.text)) {
                etemail.error = "Enter your email"
                etemail.requestFocus()
                return@setOnClickListener
            } else if (TextUtils.isEmpty(etpassword.text)) {
                etpassword.error = "Enter your password"
                etpassword.requestFocus()
                return@setOnClickListener
            } else if (TextUtils.isEmpty(etconpassword.text)) {
                etconpassword.error = "Re-enter your password"
                etconpassword.requestFocus()
                return@setOnClickListener
            } else {

                val username = etusername.text.toString()
                val email = etemail.text.toString()
                val password = etpassword.text.toString()
                val etConfirmPassword = etconpassword.text.toString()


                if (password != etConfirmPassword) {
                    etpassword.error = "Password does not match"
                    etpassword.requestFocus()
                    return@setOnClickListener
                } else {

                    val user = User(
                        username = username, email = email, password = password
                    )

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val repository = UserRepository()
                            val response = repository.registerUser(user)
                            if (response.success == true) {

                                withContext(Dispatchers.Main) {
                                    Toast.makeText(
                                        this@SignupActivity,
                                        "Successfully registered",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                                }

                            }
                        } catch (ex: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@SignupActivity,
                                    ex.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    }
                }
            }
        }
    }
}