package com.example.firebase_authentication_application

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase_authentication_application.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupButton.setOnClickListener {
            getData()
        }
        binding.signinActivity.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.googleSignUp.setOnClickListener {
            Toast.makeText(this, "Google Sign Up", Toast.LENGTH_SHORT).show()
        }
        binding.facebookSignUp.setOnClickListener {
            Toast.makeText(this, "Facebook Sign Up", Toast.LENGTH_SHORT).show()
        }
        binding.githubSignUp.setOnClickListener {
            Toast.makeText(this, "GitHub Sign Up", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getData() {
        val name = binding.nameEditText.text.toString()
        val address = binding.addressEditText.text.toString()
        val emailAddress = binding.emailAddressEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        val confirmPassword = binding.confirmPasswordEditText.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            binding.emailAddressEditText.error = "Email is invalid"
            return
        }
        if (password.isEmpty()) {
            binding.passwordEditText.error = "Password is required"
            return
        }
        if (password.length < 6) {
            binding.passwordEditText.error = "Password must be 6 or above characters long"
            return
        }
        if (password != confirmPassword) {
            binding.passwordEditText.error = "Password doesn't match"
            binding.confirmPasswordEditText.error = "Password doesn't match"
            return
        }

        createAccountWithFirebase(name, address, emailAddress, password)
    }

    private fun createAccountWithFirebase(name: String, address: String, emailAddress: String, password: String) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(emailAddress, password)
            .addOnSuccessListener {
                Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show()
                // new activity
                binding.passwordEditText.text?.clear()
                binding.confirmPasswordEditText.text?.clear()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                binding.passwordEditText.text?.clear()
                binding.confirmPasswordEditText.text?.clear()
            }
    }
}