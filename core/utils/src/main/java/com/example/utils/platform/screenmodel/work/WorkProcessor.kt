package com.example.utils.platform.screenmodel.work

import com.example.utils.functional.Either
import com.example.utils.platform.screenmodel.contract.BaseAction
import com.example.utils.platform.screenmodel.contract.BaseUiEffect

/**
 * Created by Amit on 30-05-2025.
 */
interface WorkProcessor<C: WorkCommand,A: BaseAction,E: BaseUiEffect>{
    suspend fun work(command:C) : WorkResult<A,E>
}

interface WorkCommand : BackgroundWorkKey

typealias WorkResult<A,E> = Either<A,E>

typealias ActionResult<A> = Either.Left<A>

typealias EffectResult<F> = Either.Right<F>


