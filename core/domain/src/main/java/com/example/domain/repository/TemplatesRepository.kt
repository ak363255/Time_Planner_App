package com.example.domain.repository

import com.example.domain.entities.Template.Template
import kotlinx.coroutines.flow.Flow

interface TemplatesRepository {
    suspend fun addTemplate(templates: List<Template>)
    suspend fun addTemplate(template: Template): Int
    suspend fun fetchTemplatesById(templateId: Int): Template?
    suspend fun fetchAllTemplate(): Flow<List<Template>>
    suspend fun updateTemplate(template: Template)
    suspend fun deleteTemplateById(id: Int)
    suspend fun deleteAllTemplates(): List<Template>

}