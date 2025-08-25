package com.example.impl.domain.interactors

import com.example.impl.domain.entities.EditModel
import com.example.impl.domain.repositories.EditorRepository

interface EditorInteractor {
    fun fetchEditModel(): EditModel
    fun sendEditModel(model : EditModel)

    class Base constructor(
        private val editorRepository: EditorRepository
    ): EditorInteractor{
        override fun fetchEditModel(): EditModel {
            return editorRepository.fetchEditModel();
        }

        override fun sendEditModel(model: EditModel) {
            return editorRepository.saveEditModel(model)
        }

    }
}
