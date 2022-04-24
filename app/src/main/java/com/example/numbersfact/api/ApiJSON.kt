package com.example.numbersfact.api

data class ApiJSON(
    val text:String,
    val number:Double, // Сюда прилетают числа больше, чем Long
    val found:Boolean,
    val type:String
)
