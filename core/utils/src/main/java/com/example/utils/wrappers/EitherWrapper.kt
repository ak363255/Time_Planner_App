package com.example.utils.wrappers

import com.example.utils.functional.DomainFailures
import com.example.utils.functional.Either
import com.example.utils.handlers.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * Created by Amit on 06-06-2025.
 */

interface EitherWrapper<F:DomainFailures>{
    suspend fun <O> wrap(block : suspend  () ->O):Either<F,O>

    abstract class Abstract<F:DomainFailures>(
        private val errorHandler: ErrorHandler<F>
    ):EitherWrapper<F>{
        override suspend fun <O> wrap(block: suspend () -> O): Either<F, O> = try{
            Either.Right(data = block.invoke())
        }catch (error : Throwable){
            Either.Left(data = errorHandler.handle(error))
        }
    }
}

interface FlowEitherWrapper<F:DomainFailures>: EitherWrapper<F>{
     fun <O> wrapFlow(block : suspend () ->Flow<O>): Flow<Either<F,O>>
    abstract class Abstract<F:DomainFailures>(
        private val errorHandler: ErrorHandler<F>,
    ): FlowEitherWrapper<F>, EitherWrapper.Abstract<F>(errorHandler) {
        override fun <O> wrapFlow(block: suspend () -> Flow<O>): Flow<Either<F, O>> = flow{
           block.invoke().catch { error ->
               this@flow.emit(Either.Left(data = errorHandler.handle(error)))
           } .collect{data ->
               emit(Either.Right(data = data))
           }
        }
    }
}