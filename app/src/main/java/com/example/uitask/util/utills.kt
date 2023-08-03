package com.example.uitask.util

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import com.example.uitask.R

fun showSuccessToast(context: Context) {
    val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val layout = inflater.inflate(R.layout.custom_toast_layout, null)

    val toast = Toast(context)
    toast.duration = Toast.LENGTH_SHORT
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.view = layout
    toast.show()
}