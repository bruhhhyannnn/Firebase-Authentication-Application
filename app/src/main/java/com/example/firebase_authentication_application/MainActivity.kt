package com.example.firebase_authentication_application

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase_authentication_application.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}