package com.example.data.datasources.templates

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.models.template.RepeatTimeEntity
import com.example.data.models.template.TemplateCompound
import com.example.data.models.template.TemplateDetails
import com.example.data.models.template.TemplateEntity
import com.example.data.models.template.allRepeatTimes
import com.example.data.models.template.allTemplatesId
import kotlinx.coroutines.flow.Flow

@Dao
interface TemplatesDao {
    @Insert(entity = TemplateEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrUpdateTemplates(templates : List<TemplateEntity>) : List<Long>

    @Insert(entity = RepeatTimeEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrUpdateRepeatTime(repeatTimes:List<RepeatTimeEntity>):List<Long>

    @Transaction
    suspend fun addOrUpdateCompoundTemplates(templates : List<TemplateCompound>): List<Long>{
        deleteRepeatTimeByTemplates(templates.allTemplatesId())
        addOrUpdateRepeatTime(templates.allRepeatTimes())
        return addOrUpdateTemplates(templates.map { it.template })
    }

    @Transaction
    @Query("SELECT * FROM timeTaskTemplates")
    fun fetchAllTemplates() : Flow<List<TemplateDetails>>

    @Transaction
    @Query("SELECT * FROM timeTaskTemplates WHERE id = :templateId")
    fun fetchTemplateById(templateId : Int): TemplateDetails?

    @Query("DELETE FROM timeTaskTemplates")
    suspend fun deleteAllTemplates()

    @Query("DELETE FROM repeatTimes WHERE template_id IN (:templatesId)")
    suspend fun deleteRepeatTimeByTemplates(templatesId:List<Int>)

    @Query("DELETE FROM repeatTimes")
    suspend fun deleteAllRepeatTimes()

    @Query("DELETE FROM timeTaskTemplates WHERE id = :id")
    suspend fun deleteTemplate(id: Int)

}