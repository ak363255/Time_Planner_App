package com.example.impl.data.repositories

import com.example.impl.data.datasources.EditorLocalDataSource
import com.example.impl.domain.entities.EditModel
import com.example.impl.domain.repositories.EditorRepository

class EditorRepositoryImpl (
    private val localDataSource : EditorLocalDataSource
): EditorRepository{
    override fun fetchEditModel(): EditModel {
       return localDataSource.fetchEditModel()
    }

    override fun saveEditModel(model: EditModel) {
        localDataSource.saveEditMode(model)
    }
}