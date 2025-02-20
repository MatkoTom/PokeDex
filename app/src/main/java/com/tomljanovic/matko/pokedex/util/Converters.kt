package com.tomljanovic.matko.pokedex.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {
    @TypeConverter
    fun fromStringList(list: List<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toStringList(data: String): List<String> {
        return Gson().fromJson(data, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromStatsMap(stats: Map<String, Int>): String {
        return Gson().toJson(stats)
    }

    @TypeConverter
    fun toStatsMap(statsString: String): Map<String, Int> {
        val listType = object : TypeToken<Map<String, Int>>() {}.type
        return Gson().fromJson(statsString, listType)
    }
}