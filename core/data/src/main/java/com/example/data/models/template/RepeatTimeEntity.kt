package com.example.data.models.template

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entities.Template.RepeatTimeType
import com.example.utils.functional.Month
import com.example.utils.functional.WeekDay

@Entity(tableName = "repeatTimes")
data class RepeatTimeEntity(
    @PrimaryKey(autoGenerate = true)val id: Int = 0,
    @ColumnInfo("template_id", index = true) val templateId: Int,
    @ColumnInfo("type") val type: RepeatTimeType,
    @ColumnInfo("day") val day: WeekDay? = null,
    @ColumnInfo("day_number") val dayNumber: Int? = null,
    @ColumnInfo("month") val month: Month? = null,
    @ColumnInfo("week_number") val weekNumber: Int? = null,
)
