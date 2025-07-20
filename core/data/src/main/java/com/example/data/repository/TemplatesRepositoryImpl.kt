package com.example.data.repository

import com.example.data.datasources.templates.TemplatesLocalDataSource
import com.example.data.mappers.template.mapToData
import com.example.data.mappers.template.mapToDomain
import com.example.domain.entities.Template.Template
import com.example.domain.repository.TemplatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TemplatesRepositoryImpl(
    private val localDataSource : TemplatesLocalDataSource
) : TemplatesRepository{
    override suspend fun addTemplate(templates: List<Template>) {
         localDataSource.createTemplates(templates.map { it.mapToData() })
    }

    override suspend fun addTemplate(template: Template): Int {
      return  localDataSource.createTemplates(listOf(template.mapToData()))[0].toInt()
    }


    override suspend fun fetchTemplatesById(templateId: Int): Template? {
      return localDataSource.fetchTemplatesById(templateId)?.mapToDomain()
    }

    override suspend fun fetchAllTemplate(): Flow<List<Template>> {
       return localDataSource.fetchAllTemplates().map { templates ->
           templates.map { template -> template.mapToDomain() }

       }
    }

    override suspend fun updateTemplate(template: Template) {
       localDataSource.updateTemplate(template.mapToData())
    }

    override suspend fun deleteTemplateById(id: Int) {
        localDataSource.deleteTemplateById(id)
    }

    override suspend fun deleteAllTemplates(): List<Template> {
       return localDataSource.deleteAllTemplates().map { it.mapToDomain() }
    }
}