package com.example.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        private const val CHANEL_NAME = "myChanel"
        private const val CHANNEL_DESCRIPTION = "myChanel"
        private const val CHANNEL_ID = "Chanel ID"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("firebase","token==$token")
        //토큰이 변경 될 경우 작업
    }

    override fun onMessageReceived(message: RemoteMessage) { //메시지 처
        super.onMessageReceived(message)
        //8.0 이상일 경우 채널 생성
        //채널은 앱 실행할 때 생성 하는 것이 좋음
        createNotificationChannel()

        val type = message.data["type"]?.let {
            NotificationType.valueOf(it)
        }

        if (type != null) {
            NotificationManagerCompat.from(this)
                .notify(type.notificationId, createNotification(type, message))
        }

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channel.description = CHANNEL_DESCRIPTION

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(type: NotificationType, message: RemoteMessage): Notification {
        val title = message.data["title"]
        val notificationMessage = message.data["message"]
        val imageUrl = message.data["imageUrl"]

        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(notificationMessage)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        when (type) {
            NotificationType.EXPANDABLE -> {
                notificationBuilder.setStyle(
                    NotificationCompat.BigTextStyle().bigText(notificationMessage)
                )
            }
            NotificationType.IMAGE -> {
                val futureTarget = Glide.with(this)
                    .asBitmap()
                    .load(imageUrl)
                    .centerCrop()
                    .submit()

                val bitmap = futureTarget.get()

                notificationBuilder
                    .setLargeIcon(bitmap)
                    .setStyle(
                        NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmap)
                            .bigLargeIcon(null)
                    )
            }
            else -> {}
        }
        return notificationBuilder.build()
    }
}