package com.example.data.models.categories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "subCategories",
    foreignKeys = [
        ForeignKey(
            entity = MainCategoryEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("main_category_id"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SubCategoryEntity(
    @PrimaryKey(autoGenerate = true)val id:Int = 0,
    @ColumnInfo("main_category_id", index = true)val mainCategoryId: Int,
    @ColumnInfo("sub_category_name") val subCategoryName: String,
    @ColumnInfo("sub_description") val description: String?,
    )
