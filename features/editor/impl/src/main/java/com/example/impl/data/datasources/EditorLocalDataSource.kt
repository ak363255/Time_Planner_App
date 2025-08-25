package com.example.impl.data.datasources

import com.example.impl.domain.entities.EditModel

interface EditorLocalDataSource {
    fun saveEditMode(model : EditModel)
    fun fetchEditModel(): EditModel
    class Base : EditorLocalDataSource{
        private var currentValue: EditModel? = null;
        override fun saveEditMode(model: EditModel) {
            currentValue = model;
        }

        override fun fetchEditModel(): EditModel {
            return checkNotNull(currentValue){"Error transfer EditModel between features"}
        }

    }
}