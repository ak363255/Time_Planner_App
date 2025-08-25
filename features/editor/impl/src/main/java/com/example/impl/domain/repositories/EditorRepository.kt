package com.example.impl.domain.repositories

import com.example.impl.domain.entities.EditModel

interface EditorRepository {
    fun fetchEditModel(): EditModel
    fun saveEditModel(model: EditModel)
}