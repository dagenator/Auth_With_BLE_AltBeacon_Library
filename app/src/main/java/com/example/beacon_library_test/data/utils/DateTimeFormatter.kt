package com.example.beacon_library_test.data.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class DateTimeFormatter @Inject constructor() {

    fun stringToDateTime(string: String): LocalDateTime {
        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return LocalDateTime.parse(string, pattern)
    }

    fun getMoscowTime(): LocalDateTime {
        return LocalDateTime.parse(LocalDateTime.now(ZoneId.of("Europe/Moscow")).toString())
    }

}