package ru.andlancer.todofl.repository.converter

import android.icu.util.UniversalTimeScale.toLong
import androidx.room.TypeConverter

import java.lang.Exception
import java.text.SimpleDateFormat

import java.util.*


class DateConverter {

    @TypeConverter
    fun stringToDate(value: String?): Date {
        if (value == null) {
            return Date()
        }
            //return SimpleDateFormat("yyyy-MM-dd").parse(value)
        return Date()
    }

    @TypeConverter
    fun dateToString(cal: Date?): String {
         if (cal == null) {
             return  SimpleDateFormat("yyyy-MM-dd").format(Date())
        } else {
            try {
                return SimpleDateFormat("yyyy-MM-dd").format(cal)
            }
            catch (e: Exception){

            }
             try {
                 return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(cal)
             }
             catch (e: Exception){

             }
             return ""
        }

    }

    @TypeConverter
    fun convertLongToTime(date: Long): Date {
        val date = Date(date)
        return date
    }

    @TypeConverter
    fun convertDateToLong(date: Date): Long {
        return date.time
    }

}