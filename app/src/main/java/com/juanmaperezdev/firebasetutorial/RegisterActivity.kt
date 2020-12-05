package com.juanmaperezdev.firebasetutorial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailRegister: EditText
    private lateinit var passwordRegister: EditText
    private lateinit var passwordRepeatRegister: EditText
    private lateinit var buttonRegister: Button
    private lateinit var buttonGoLogin: Button

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        emailRegister = findViewById(R.id.emailRegister)
        passwordRegister = findViewById(R.id.passwordRegister)
        passwordRepeatRegister = findViewById(R.id.passwordRepeatRegister)
        buttonRegister = findViewById(R.id.buttonRegister)
        buttonGoLogin = findViewById(R.id.buttonGoLogin)

        buttonRegister.setOnClickListener {
            if(passwordRegister.text.toString().equals(passwordRepeatRegister.text.toString()) &&
                    checkEmpty(emailRegister.text.toString(), passwordRegister.text.toString(), passwordRepeatRegister.text.toString())) {
                register(emailRegister.text.toString(), passwordRegister.text.toString())
            }
        }

        buttonGoLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun checkEmpty(email: String, pass: String, repeatPass: String): Boolean {
        return email.isNotEmpty() && pass.isNotEmpty() && repeatPass.isNotEmpty()
    }


    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Log.d("PRUEBA", task.exception!!.message!!)
                    Toast.makeText(applicationContext, "Register failed!", Toast.LENGTH_LONG).show()
                }

            }
    }
}