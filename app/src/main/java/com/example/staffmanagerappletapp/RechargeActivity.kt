package com.example.staffmanagerappletapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.staffmanagerappletapp.databinding.ActivityRechargeBinding

class RechargeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRechargeBinding
    private val sharedPreferenceHelper:SharedPreferenceHelper by lazy { SharedPreferenceHelper(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRechargeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.edtNumberCard.setText(sharedPreferenceHelper.getNumberCard())
    }
}