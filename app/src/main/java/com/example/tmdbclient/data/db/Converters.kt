package com.example.tmdbclient.data.db

import androidx.room.TypeConverter
import com.example.tmdbclient.data.model.artist.KnownFor
import com.google.gson.Gson


class Converters {
    @TypeConverter
    fun strListAPI2DB(value : List<String>?): String? {
        return if (value==null)
            null
        else{
            Gson().toJson(value)
        }
    }

    @TypeConverter
    fun strListDB2API(value: String?): List<String>? {
        return if (value==null)
            null
        else{
            Gson().fromJson(value, Array<String>::class.java).toList()
        }
    }

    @TypeConverter
    fun intListAPI2DB(value : List<Int>?): String? {
        return if (value==null)
            null
        else{
            Gson().toJson(value)
        }
    }

    @TypeConverter
    fun intListDB2API(value: String?): List<Int>? {
        return if (value==null)
            null
        else{
            Gson().fromJson(value, Array<Int>::class.java).toList()
        }
    }

    @TypeConverter
    fun knowForListAPI2DB(value : List<KnownFor>?): String? {
        return if (value==null)
            null
        else{
            Gson().toJson(value)
        }
    }

    @TypeConverter
    fun knowForListDB2API(value: String?): List<KnownFor>? {
        return if (value==null)
            null
        else{
            Gson().fromJson(value, Array<KnownFor>::class.java).toList()
        }
    }
}
