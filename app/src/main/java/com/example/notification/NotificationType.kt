package com.example.notification

enum class NotificationType(val title: String?, val notificationId : Int) {
    NORMAL("일반",0),
    EXPANDABLE("확장형",1),
    IMAGE("이미지",2)
}