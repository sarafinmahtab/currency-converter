package com.develop.currencyconverter.helper

import android.content.res.Resources
import androidx.annotation.RawRes
import java.io.*


/*
 * Created by Arafin Mahtab on 11/10/2021.
 * Copyright (c) 2021 Rangan Apps. All rights reserved.
 */

fun Resources.getJsonFromRawResource(@RawRes resourcesId: Int): String {
    val inputStream: InputStream = openRawResource(resourcesId)
    val writer = StringWriter()
    val buffer = CharArray(1024)

    inputStream.use { rawData ->
        val reader: Reader = BufferedReader(InputStreamReader(rawData, "UTF-8"))
        var length: Int
        while (reader.read(buffer).also { length = it } != -1) {
            writer.write(buffer, 0, length)
        }
    }

    return writer.toString()
}
