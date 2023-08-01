package com.example.uitask.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun getSystemDate(): String {
    val currentDate = Calendar.getInstance().time
    val dateFormat =
        SimpleDateFormat("yyyy, MMMM d", Locale.ENGLISH)
    return dateFormat.format(
        currentDate
    )

}

fun dateConverter(dateString: String): String {

    // Step 1: Parse the input date using the original format
    val inputDateFormat = SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
    val parsedDate: Date = inputDateFormat.parse(dateString)

    // Step 2: Format the parsed date into the desired output format
    val outputDateFormat = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.ENGLISH)

    return outputDateFormat.format(parsedDate)
}