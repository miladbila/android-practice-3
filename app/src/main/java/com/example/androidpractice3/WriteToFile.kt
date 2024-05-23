package com.example.androidpractice3

import android.content.Context
import android.os.Environment
import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.util.Calendar

class FileWriter() {

    fun writeData(type: String, value: String) {
        val jsonObject = JSONObject()
        jsonObject.put("type", type)
        jsonObject.put("time", Calendar.getInstance().time)
        jsonObject.put("value", value)

        val fileName = "info.json"
        val folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(folder, fileName)
        try {
            file.createNewFile()
            file.appendText(jsonObject.toString())
            file.appendText("\n")
        } catch (e: Exception) {
            Log.e("FileWriter", "Error writing to file: ${e.message}")
        }
    }

    fun readData(): List<Data>{
        val fileName = "info.json"
        val folder =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val file = File(folder, fileName)
        if (!file.exists()) {
            return mutableListOf()
        }
        val fileReader = FileReader(file)
        val bufferedReader = BufferedReader(fileReader)
        var line = bufferedReader.readLine()
        val data = mutableListOf<Data>()
        while (line != null) {
            val jsonObject = JSONObject(line)
            data.add(
                Data(
                    jsonObject.get("type").toString(),
                    jsonObject.get("time").toString(),
                    jsonObject.get("value").toString()
                )
            )
            line = bufferedReader.readLine()
        }
        bufferedReader.close()
        return data
    }
}