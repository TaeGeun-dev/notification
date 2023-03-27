package com.example.notification

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.notification.databinding.ActivityMainBinding
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("firebase", "Token == ${it.result}")
            }
        }
        notificaitonResult()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent) // 새로운 인텐트로 갱신

        notificaitonResult(true)
    }

    private fun notificaitonResult(isNewIntent: Boolean = false) {
        binding.screenResultTV.text = (intent.getStringExtra("notificationType") ?: "아이콘 클릭") +
                if (isNewIntent) {
                    "으로 갱신 되었습니다."
                } else {
                    "으로 실행 되었습니다"
                }
    }
}