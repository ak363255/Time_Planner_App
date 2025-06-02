package com.example.utils.functional

import kotlinx.coroutines.flow.Flow


sealed class Either<out L,out R>{
    data class Left<L>(val data:L): Either<L, Nothing>()
    data class Right<R>(val data:R): Either<Nothing,R>()
    val isLeft = this is Left
    val isRight = this is Right
}

typealias DomainResult<L,R> = Either<L,R>

typealias FlowDomainResult<L,R> = Flow<Either<L,R>>

typealias UnitDomainResult<L> = Either<L,Unit>

fun <L,R> Either<L,R>.leftOrElse(elseValue:L):L = when(this){
    is Either.Left -> this.data
    is Either.Right -> elseValue
}

suspend fun <L,R> Either<L,R>.handle(
    onLeftAction : suspend  (L) -> Unit = {},
    onRightAction : suspend (R) -> Unit = {}
) = when (this){
    is Either.Left -> onLeftAction(this.data)
    is Either.Right -> onRightAction(this.data)
}

suspend fun <L, R, T> Either<L, R>.handleAndGet(
    onLeftAction: suspend (L) -> T,
    onRightAction: suspend (R) -> T,
) = when (this) {
    is Either.Left -> onLeftAction(this.data)
    is Either.Right -> onRightAction(this.data)
}

suspend fun <L, R> Either<L, R>.rightOrError(message: String) = handleAndGet(
    onLeftAction = { error(message) },
    onRightAction = { it },
)


suspend fun <L, R> Flow<Either<L, R>>.collectAndHandle(
    onLeftAction: suspend (L) -> Unit = {},
    onRightAction: suspend (R) -> Unit = {},
) = collect { either ->
    either.handle(onLeftAction, onRightAction)
}
