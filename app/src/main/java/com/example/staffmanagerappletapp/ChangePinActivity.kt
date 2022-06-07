package com.example.staffmanagerappletapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.staffmanagerappletapp.databinding.ActivityChangePinBinding

class ChangePinActivity : AppCompatActivity() {
    private lateinit var binding:ActivityChangePinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePinBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}