package com.example.data.datasources.templates

import com.example.data.models.template.TemplateCompound
import com.example.data.models.template.TemplateDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface TemplatesLocalDataSource {
    suspend fun createTemplates(templates: List<TemplateCompound>): List<Long>
    suspend fun fetchTemplatesById(templateId : Int): TemplateDetails?
    fun fetchAllTemplates() : Flow<List<TemplateDetails>>
    suspend fun updateTemplate(template: TemplateCompound)
    suspend fun deleteTemplateById(id: Int)
    suspend fun deleteAllTemplates() : List<TemplateDetails>

    class Base(
        private val templatesDao : TemplatesDao
    ): TemplatesLocalDataSource{
        override suspend fun createTemplates(templates: List<TemplateCompound>): List<Long> {
            return templatesDao.addOrUpdateCompoundTemplates(templates)
        }

        override suspend fun fetchTemplatesById(templateId: Int): TemplateDetails? {
            return templatesDao.fetchTemplateById(templateId)

        }

        override fun fetchAllTemplates(): Flow<List<TemplateDetails>> {
            return templatesDao.fetchAllTemplates()
        }

        override suspend fun updateTemplate(template: TemplateCompound) {
           templatesDao.addOrUpdateCompoundTemplates(listOf(template))
        }

        override suspend fun deleteTemplateById(id: Int) {
           templatesDao.deleteTemplate(id)
            templatesDao.deleteRepeatTimeByTemplates(listOf(id))
        }

        override suspend fun deleteAllTemplates(): List<TemplateDetails> {
            val deletableTemplates = fetchAllTemplates().first()
            templatesDao.deleteAllTemplates()
            templatesDao.deleteAllTemplates()
            templatesDao.deleteAllRepeatTimes()
            return deleteAllTemplates()
        }

    }
}