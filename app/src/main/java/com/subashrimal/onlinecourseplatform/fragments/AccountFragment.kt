package com.subashrimal.onlinecourseplatform.fragments
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.subashrimal.onlinecourseplatform.R
import com.subashrimal.onlinecourseplatform.UpdateProfileActivity
import com.subashrimal.onlinecourseplatform.api.ServiceBuilder
import com.subashrimal.onlinecourseplatform.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AccountFragment : Fragment() {
    private lateinit var txtusername : TextView
    private lateinit var txtemail : TextView
    private lateinit var btnEdit: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        txtusername = view.findViewById(R.id.txtusername)
        txtemail = view.findViewById(R.id.txtemail)
        btnEdit = view.findViewById(R.id.btnEdit)
//        imgProfile = view.findViewById(R.id.imgProfile)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userRepository = UserRepository()
                val response = userRepository.getSingleUser()
                if (response.success == true) {
                    val user = response.data!!
                    val image = ServiceBuilder.loadImagePath() + user.profilePic
                    withContext(Dispatchers.Main) {
                        txtusername.setText("${response.data?.username}")
                        txtemail.setText("${response.data?.email}")
//                        if (!user.image.equals("")) {
//                            Glide.with(context!!)
//                                .load(image)
//                                .fitCenter()
//                                .into(imgProfile)
//                        }
                    }
                }
            } catch (ex: Exception) {
            }
        }
        btnEdit.setOnClickListener {
            val intent = Intent(context, UpdateProfileActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}