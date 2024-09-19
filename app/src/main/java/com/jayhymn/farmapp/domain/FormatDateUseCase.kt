package com.jayhymn.farmapp.domain

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class FormatDateUseCase @Inject constructor() {
    private val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private val outputFormat = SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.getDefault())

    operator fun invoke(dateString: String): String {
        return try {
            val date = inputFormat.parse(dateString)
            date?.let { outputFormat.format(it) } ?: ""
        } catch (e: Exception) {
            Log.e("FormatDateUseCase", e.message, e)
            ""
        }
    }
}