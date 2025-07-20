package com.example.data.models.tasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.data.models.categories.MainCategoryEntity
import com.example.data.models.categories.SubCategoryEntity

@Entity(
    tableName = "undefinedTasks",
    foreignKeys = [
        ForeignKey(
            entity = MainCategoryEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("main_category_id"),
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = SubCategoryEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("sub_category_id"),
            onDelete = ForeignKey.SET_NULL,
        ),
    ],
)
data class UndefinedTaskEntity(
    @PrimaryKey(autoGenerate = false) val key: Long = 0L,
    @ColumnInfo("created_at") val createdAt: Long? = null,
    @ColumnInfo("deadline") val deadline: Long? = null,
    @ColumnInfo("main_category_id", index = true) val mainCategoryId: Int,
    @ColumnInfo("sub_category_id", index = true) val subCategoryId: Int?,
    @ColumnInfo("is_important") val isImportantMax: Boolean,
    @ColumnInfo("is_medium_important", defaultValue = "0") val isImportantMedium: Boolean,
    @ColumnInfo("note") val note: String? = null,
)
