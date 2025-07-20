package com.example.data.models.template

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.data.models.categories.MainCategoryEntity
import com.example.data.models.categories.SubCategoryEntity

@Entity(
    tableName = "timeTaskTemplates",
    foreignKeys = [
        ForeignKey(
            entity = MainCategoryEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("main_category_id"),
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SubCategoryEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("sub_category_id"),
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class TemplateEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("start_time") val startTime: Long,
    @ColumnInfo("end_time") val endTime: Long,
    @ColumnInfo("main_category_id", index = true) val categoryId: Int,
    @ColumnInfo("sub_category_id", index = true) val subCategoryId: Int?,
    @ColumnInfo("is_important", defaultValue = "0") val isImportantMax: Boolean = false,
    @ColumnInfo("is_medium_important") val isImportantMedium: Boolean,
    @ColumnInfo("is_enable_notification") val isEnableNotification: Boolean,
    @ColumnInfo("is_consider_in_statistics") val isConsiderInStatistics: Boolean,
    @ColumnInfo("repeat_enabled", defaultValue = "0") val repeatEnabled: Boolean = false,

    )