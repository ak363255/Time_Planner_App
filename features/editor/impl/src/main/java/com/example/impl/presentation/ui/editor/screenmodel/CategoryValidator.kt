package com.example.impl.presentation.ui.editor.screenmodel

import com.example.impl.presentation.models.categories.editor.MainCategoryUi
import com.example.utils.validation.ValidateError
import com.example.utils.validation.ValidateResult
import com.example.utils.validation.Validator
import kotlinx.parcelize.Parcelize

internal interface CategoryValidator : Validator<MainCategoryUi, CategoryValidateError>{
    class Base(): CategoryValidator{
        override fun validate(data: MainCategoryUi): ValidateResult<CategoryValidateError> {
            return if(data.id ==0){
                ValidateResult(false, CategoryValidateError.EmptyCategoryError)

            }else{
                ValidateResult(true,null)
            }
        }
    }
}
@Parcelize
sealed class CategoryValidateError : ValidateError{
    object EmptyCategoryError : CategoryValidateError()
}