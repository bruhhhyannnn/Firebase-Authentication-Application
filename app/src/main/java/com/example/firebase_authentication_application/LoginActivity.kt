package com.example.firebase_authentication_application

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase_authentication_application.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        binding.loginButton.setOnClickListener {
            getData()
        }
        binding.signupActivity.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }
        binding.forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
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
        if (FirebaseAuth.getInstance().currentUser != null && FirebaseAuth.getInstance().currentUser?.isEmailVerified == true) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun setInProgress(inProgress: Boolean) {
        if (inProgress) {
            binding.progressBar.visibility = View.VISIBLE
            binding.loginButton.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.loginButton.visibility = View.VISIBLE
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
            return
        }

        loginWithFirebase(emailAddress, password)
    }

    private fun loginWithFirebase(emailAddress: String, password: String) {
        setInProgress(true)
        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(emailAddress, password)
            .addOnSuccessListener {
                val verification = FirebaseAuth.getInstance().currentUser?.isEmailVerified
                if (verification == true) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    binding.emailAddressEditText.text?.clear()
                    binding.passwordEditText.text?.clear()
                    setInProgress(false)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Please verify the link from your Email first!", Toast.LENGTH_SHORT).show()
                    setInProgress(false)
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Wrong Email or Password", Toast.LENGTH_SHORT).show()
                setInProgress(false)
            }
    }
}