package com.example.uitask.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getSystemDate(): String {
    val currentDate = Calendar.getInstance().time
    val dateFormat =
        SimpleDateFormat("yyyy, MMMM d", Locale.ENGLISH)
    return dateFormat.format(
        currentDate
    )

}