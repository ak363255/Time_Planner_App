package com.example.data.models.categories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.entities.categories.DefaultCategoryType

@Entity(tableName = "mainCategories")
data class MainCategoryEntity(
    @PrimaryKey(autoGenerate = true)val id : Int = 0,
    @ColumnInfo("custom_name") val customName : String?,
    @ColumnInfo("default_category_type") val defaultType: DefaultCategoryType?
)
