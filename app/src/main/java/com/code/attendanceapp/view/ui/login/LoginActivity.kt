package com.code.attendanceapp.view.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.code.attendanceapp.R
import com.code.attendanceapp.databinding.ActivityLoginBinding
import com.code.attendanceapp.view.ui.homepage.MainActivity
import com.code.attendanceapp.view.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //bikin instance firabase
        firebaseAuth = FirebaseAuth.getInstance()

        binding.apply {

            //event ketika tombol login di klik
            buttonLogin.setOnClickListener{
                val email = editTextEmailLogin.text.toString()
                val password = editTextPasswordLogin.text.toString()

                //cek field
                if(email.isNotEmpty() && password.isNotEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener{result ->
                        //jika berhasik pindah activity
                        if(result.isSuccessful){
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                        }else{  //jika gagal tampilkan message
                            response("Ada yang salah!")
                        }
                    }
                }else{  //message ketika user tidak mengisi field
                    response("Tidak boleh ada data yang kosong!")
                }
            }

            //pergi ke dafatar
            tvDaftar.setOnClickListener{
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        }

        //session check
        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun response(message: String){
        Toast.makeText(this@LoginActivity, message, Toast.LENGTH_SHORT).show()
    }
}