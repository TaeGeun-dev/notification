package com.example.notification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.compose.runtime.*
import androidx.databinding.DataBindingUtil
import com.example.notification.databinding.ActivityMainBinding
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful){
                Log.d("firebase","Token == ${it.result}")
            }
        }
    }
}