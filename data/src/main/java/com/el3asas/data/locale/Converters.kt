package com.el3asas.data.locale

import androidx.room.TypeConverter
import com.el3asas.data.locale.entities.SourceEntityField
import com.google.gson.Gson


class Converters {
    @TypeConverter
    fun sourceToString(sourceEntityField: SourceEntityField): String = Gson().toJson(sourceEntityField)

    @TypeConverter
    fun stringToSource(string: String): SourceEntityField = Gson().fromJson(string, SourceEntityField::class.java)

}