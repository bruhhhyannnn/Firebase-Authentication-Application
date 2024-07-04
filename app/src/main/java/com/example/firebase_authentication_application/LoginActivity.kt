package com.example.firebase_authentication_application

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase_authentication_application.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            getData()
        }
        binding.signupActivity.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }
        binding.forgotPassword.setOnClickListener {

        }
        binding.googleSignIn.setOnClickListener {
            Toast.makeText(this, "Google Sign In", Toast.LENGTH_SHORT).show()
            // TODO: implement Google Sign In
        }
        binding.facebookSignIn.setOnClickListener {
            Toast.makeText(this, "Facebook Sign In", Toast.LENGTH_SHORT).show()
            // TODO: implement Facebook Sign In
        }
        binding.githubSignIn.setOnClickListener {
            Toast.makeText(this, "GitHub Sign In", Toast.LENGTH_SHORT).show()
            // TODO: implement GitHub Sign In
        }
    }

    override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun getData() {
        val emailAddress = binding.emailAddressEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            binding.emailAddressEditText.error = "Email is invalid"
            return
        }
        if (password.isEmpty()) {
            binding.passwordEditText.error = "Password is required"
            return
        }
        if (password.length < 6) {
            binding.passwordEditText.error = "Password must be at least 6 characters"
        }

        loginWithFirebase(emailAddress, password)
    }

    private fun loginWithFirebase(emailAddress: String, password: String) {
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(emailAddress, password)
            .addOnSuccessListener {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                binding.emailAddressEditText.text.clear()
                binding.passwordEditText.text.clear()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
    }
}