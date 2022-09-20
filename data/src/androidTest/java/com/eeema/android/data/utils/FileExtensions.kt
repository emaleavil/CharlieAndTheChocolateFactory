package com.eeema.android.data.utils

import java.io.IOException

object FileExtensions {
    @Throws(IOException::class)
    fun readFileFromResources(fileName: String) = javaClass.classLoader
        ?.getResourceAsStream(fileName)
        ?.bufferedReader()
        ?.use { it.readText() } ?: ""
}
