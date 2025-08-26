package com.example.utils.validation

interface Validator<D, E : ValidateError> {
    fun validate(date : D): ValidateResult<E>
}