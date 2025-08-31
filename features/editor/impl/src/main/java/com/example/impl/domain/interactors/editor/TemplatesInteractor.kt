package com.example.impl.domain.interactors.editor

import com.example.domain.entities.Template.Template
import com.example.domain.repository.TemplatesRepository
import com.example.impl.domain.common.editor.EditorEitherWrapper
import com.example.impl.domain.entities.EditorFailures
import com.example.utils.functional.DomainResult
import kotlinx.coroutines.flow.first
//editor
internal interface TemplatesInteractor {

    suspend fun fetchTemplates(): DomainResult<EditorFailures, List<Template>>
    suspend fun updateTemplate(template: Template): DomainResult<EditorFailures, Unit>
    suspend fun addTemplate(template: Template): DomainResult<EditorFailures, Int>
    suspend fun deleteTemplateById(id: Int): DomainResult<EditorFailures, Unit>

    class Base(
        private val eitherWrapper: EditorEitherWrapper,
        private val templatesRepository : TemplatesRepository
    ): TemplatesInteractor{
        override suspend fun fetchTemplates(): DomainResult<EditorFailures, List<Template>> = eitherWrapper.wrap{
            templatesRepository.fetchAllTemplate().first()
        }

        override suspend fun updateTemplate(template: Template): DomainResult<EditorFailures, Unit> = eitherWrapper.wrap {
            templatesRepository.updateTemplate(template)
        }

        override suspend fun addTemplate(template: Template): DomainResult<EditorFailures, Int> = eitherWrapper.wrap{
            templatesRepository.addTemplate(template)
        }

        override suspend fun deleteTemplateById(id: Int): DomainResult<EditorFailures, Unit> = eitherWrapper.wrap {
            templatesRepository.deleteTemplateById(id)
        }

    }
}