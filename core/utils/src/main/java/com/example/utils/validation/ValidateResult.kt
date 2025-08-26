package com.example.utils.validation

data class ValidateResult<E : ValidateError>(
    val isValid : Boolean,
    val validError : E?
)

suspend fun <E: ValidateError> ValidateResult<E>.handle(
    onValid : suspend  () -> Unit,
    onError : suspend  (E) -> Unit
) = when(this.isValid){
    true -> onValid()
    false -> onError(checkNotNull(this.validError))
}