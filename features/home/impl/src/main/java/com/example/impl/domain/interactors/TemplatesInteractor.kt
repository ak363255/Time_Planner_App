package com.example.impl.domain.interactors

import com.example.domain.entities.Template.Template
import com.example.domain.entities.schedules.TimeTask
import com.example.domain.repository.TemplatesRepository
import com.example.impl.domain.common.HomeEitherWrapper
import com.example.impl.domain.entities.HomeFailures
import com.example.utils.functional.DomainResult
import com.example.utils.functional.UnitDomainResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

interface TemplatesInteractor {
    suspend fun fetchTemplate() : Flow<DomainResult<HomeFailures,List<Template>>>
    suspend fun updateTemplate(template : Template) : UnitDomainResult<HomeFailures>
    suspend fun checkIsTemplate(timeTask : TimeTask): DomainResult<HomeFailures, Template?>
    suspend fun addTemplate(template : Template): DomainResult<HomeFailures, Int>
    suspend fun  deleteTemplate(id: Int): DomainResult<HomeFailures, Unit>
    class Base (
        private val templatesRepository: TemplatesRepository,
        private val eitherWrapper : HomeEitherWrapper
    ): TemplatesInteractor{
        override suspend fun fetchTemplate(): Flow<DomainResult<HomeFailures, List<Template>>> = eitherWrapper.wrapFlow {
            templatesRepository.fetchAllTemplate()
        }

        override suspend fun updateTemplate(template: Template): UnitDomainResult<HomeFailures>  = eitherWrapper.wrap{
            templatesRepository.updateTemplate(template)
        }

        override suspend fun checkIsTemplate(timeTask: TimeTask): DomainResult<HomeFailures, Template?>  = eitherWrapper.wrap{
            templatesRepository.fetchAllTemplate().first().find {template ->
                template.equalsTemplate(timeTask)
            }

        }

        override suspend fun addTemplate(template: Template): DomainResult<HomeFailures, Int>  = eitherWrapper.wrap{
            templatesRepository.addTemplate(template)
        }

        override suspend fun deleteTemplate(id: Int): DomainResult<HomeFailures, Unit> = eitherWrapper.wrap{
            templatesRepository.deleteTemplateById(id)
        }

    }
}