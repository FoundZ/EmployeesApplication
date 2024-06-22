package com.takumi.employee.ui.utils

import android.content.Context
import android.widget.TextView
import android.widget.Toast

const val SPACE = " "

fun TextView.setTextValue(titleStringID: Int, contentValue: String) {
    this.text = titleStringID.getString(this.context).append(SPACE).append(contentValue)
}

fun Int.getString(context: Context): StringBuilder {
    return StringBuilder(context.resources.getString(this))
}


fun String.showToast(context: Context){
    Toast.makeText(context,this,Toast.LENGTH_LONG).show()
}