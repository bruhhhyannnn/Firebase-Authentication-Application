package com.example.firebase_authentication_application

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase_authentication_application.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.resetButton.setOnClickListener {
            getData()
        }
        binding.loginActivity.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.helpText.setOnClickListener {
            createDialog()
        }
    }

    private fun getData() {
        val emailAddress = binding.emailAddressEditText.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            binding.emailAddressEditText.error = "Email is invalid"
            return
        }

        resetEmailAddressFromFirebase(emailAddress)
    }

    private fun resetEmailAddressFromFirebase(emailAddress: String) {
        FirebaseAuth.getInstance()
            .sendPasswordResetEmail(emailAddress)
            .addOnSuccessListener {
                Toast.makeText(this, "Check your your email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun createDialog() {
        AlertDialog.Builder(this)
            .setTitle("Forgot Password")
            .setMessage("The entered email will be sent to your email address, if you we're able to receive it that means you have created an account using that email address, if none means you have not created an account using that email.")
            .setPositiveButton("Ok") { _, _ -> }
            .create()
            .show()
    }
}