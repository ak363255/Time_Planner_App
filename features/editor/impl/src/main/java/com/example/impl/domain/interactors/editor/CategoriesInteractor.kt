package com.example.impl.domain.interactors.editor

import com.example.domain.entities.categories.Categories
import com.example.domain.entities.categories.SubCategory
import com.example.domain.repository.CategoriesRepository
import com.example.domain.repository.SubCategoriesRepository
import com.example.impl.domain.common.editor.EditorEitherWrapper
import com.example.impl.domain.entities.EditorFailures
import com.example.utils.functional.DomainResult
import kotlinx.coroutines.flow.first

interface CategoriesInteractor {
    suspend fun fetchCategories(): DomainResult<EditorFailures, List<Categories>>
    suspend fun addSubCategory(subCategory: SubCategory): DomainResult<EditorFailures, Unit>
    class Base(
        private val categoriesRepository : CategoriesRepository,
        private val subCategoriesRepository : SubCategoriesRepository,
        private val eitherWrapper: EditorEitherWrapper
    ): CategoriesInteractor{
        override suspend fun fetchCategories(): DomainResult<EditorFailures, List<Categories>> = eitherWrapper.wrap{
            categoriesRepository.fetchCategories().first().sortedBy { it.category.id != 0 }
        }

        override suspend fun addSubCategory(subCategory: SubCategory): DomainResult<EditorFailures, Unit> = eitherWrapper.wrap {
            subCategoriesRepository.addSubCategories(listOf(subCategory))
        }

    }
}