package com.code.attendanceapp.view.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.code.attendanceapp.R
import com.code.attendanceapp.databinding.ActivityRegisterBinding
import com.code.attendanceapp.view.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.apply {
            buttonDaftar.setOnClickListener{
                val email = editTextEmailRegister.text.toString()
                val password = editTextPasswordRegister.text.toString()
                val rePassword = editTextRePasswordRegister.text.toString()

                if(email.isNotEmpty() && password.isNotEmpty() && rePassword.isNotEmpty()){
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { result ->
                        if(result.isSuccessful){
                            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                            startActivity(intent)
                            finish()
                        }else{
                            response("Ada yang salah!")
                        }
                    }
                }else{
                    response("Tidak boleh ada data yang kosong!")
                }
            }

            ivKembali.setOnClickListener{
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()
            }
        }
    }
    fun response(message: String){
        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_SHORT).show()
    }
}