package com.example.data.models.settings

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entities.settings.ColorsType
import com.example.domain.entities.settings.LanguageType
import com.example.domain.entities.settings.ThemeType

@Entity(tableName = "ThemeSettings")
data class ThemeSettingEntity (
    @PrimaryKey(autoGenerate = false) val id : Int = 0,
    @ColumnInfo("language") val language: LanguageType = LanguageType.DEFAULT,
    @ColumnInfo("theme_colors") val themeColors : ThemeType = ThemeType.DEFAULT,
    @ColumnInfo("colors_type") val colorsType : ColorsType = ColorsType.PINK,
    @ColumnInfo("dynamic_color")val isDynamicColorEnable : Boolean = false
)