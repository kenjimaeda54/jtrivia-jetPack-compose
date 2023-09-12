package com.example.jtrivia.data

data class DataOrExpection<T,Boll,Exception>(
    var data: T? = null,
    var loading: Boll? = null,
    var e: Exception? = null
)