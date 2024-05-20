package com.example.androidpractice3

import android.content.Context
import android.os.Environment
import android.util.Log
import org.json.JSONObject
import java.io.File
import java.util.Calendar

class FileWriter(private val context: Context) {
    fun writeData(type:String, value: String) {
        val jsonObject = JSONObject()
        jsonObject.put("type", type)
        jsonObject.put("time", Calendar.getInstance().time)
        jsonObject.put("value", value)

        val fileName = "info.json"
        val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//        val folder = context.filesDir
        val file = File(folder, fileName)
        try {
            file.createNewFile()
            file.appendText(jsonObject.toString())
        } catch (e: Exception) {
            Log.e("FileWriter", "Error writing to file: ${e.message}")
        }
    }
}