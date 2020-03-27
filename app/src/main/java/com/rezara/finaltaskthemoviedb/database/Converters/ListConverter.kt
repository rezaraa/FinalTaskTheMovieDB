package fariborz.rezara.database.Converters

import android.arch.persistence.room.TypeConverter

/**
Created by Reza*/
class ListConverter {
    @TypeConverter
    fun toString(list: List<Int>?): String? {
        return list!!.joinToString("|",prefix = "", postfix = "", limit = list.size, truncated = "")
    }

    @TypeConverter
    fun toList(string: String?): List<Int>? {
        val result: List<Int> = string!!.split(",").map { it.trim().toInt() }
        return result
    }

}
