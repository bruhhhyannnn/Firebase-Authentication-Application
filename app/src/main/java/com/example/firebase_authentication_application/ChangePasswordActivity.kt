package com.example.firebase_authentication_application

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase_authentication_application.databinding.ActivityChangePasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        binding.emailTextView.text = "Email: " + FirebaseAuth.getInstance().currentUser?.email.toString()

        binding.changePasswordButton.setOnClickListener {
            getData()
        }
    }

    private fun setInProgress(inProgress: Boolean) {
        if (inProgress) {
            binding.progressBar.visibility = View.VISIBLE
            binding.changePasswordButton.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.changePasswordButton.visibility = View.VISIBLE
        }
    }

    private fun getData() {
        val currentPassword = binding.currentPasswordEditText.text.toString()
        val newPassword = binding.newPasswordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        if (currentPassword.isEmpty()) {
            binding.currentPasswordEditText.error = "Password is required"
            return
        }
        if (currentPassword.length < 6) {
            binding.currentPasswordEditText.error = "Password must be 6 or above characters long"
            return
        }
        if (newPassword.isEmpty()) {
            binding.newPasswordEditText.error = "Password is required"
            return
        }
        if (newPassword.length < 6) {
            binding.newPasswordEditText.error = "Password must be 6 or above characters long"
            return
        }
        if (confirmPassword.isEmpty()) {
            binding.confirmPasswordEditText.error = "Password is required"
            return
        }
        if (confirmPassword.length < 6) {
            binding.confirmPasswordEditText.error = "Password must be 6 or above characters long"
            return
        }
        if (newPassword != confirmPassword) {
            binding.newPasswordEditText.error = "Password do not match"
            binding.confirmPasswordEditText.error = "Password do not match"
            return
        }

        reAuthenticateAndChangePassword(currentPassword, newPassword)
    }

    private fun reAuthenticateAndChangePassword(currentPassword: String, newPassword: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val credential = EmailAuthProvider.getCredential(user?.email!!, currentPassword)

        // Re-authenticate the user
        setInProgress(true)
        user.reauthenticate(credential)
            .addOnSuccessListener {

                // Change the password
                user.updatePassword(newPassword)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Password changed successfully", Toast.LENGTH_SHORT).show()
                        setInProgress(false)
                        logoutUser()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, it.localizedMessage ?: "Something went wrong", Toast.LENGTH_SHORT).show()
                        setInProgress(false)
                    }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Incorrect current password", Toast.LENGTH_SHORT).show()
                setInProgress(false)
            }
    }

    private fun logoutUser() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}