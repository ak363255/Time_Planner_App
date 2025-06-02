package com.example.utils.platform.screenmodel.work

import com.example.utils.functional.DomainFailures
import com.example.utils.functional.Either
import com.example.utils.functional.handleAndGet
import com.example.utils.platform.screenmodel.contract.BaseAction
import com.example.utils.platform.screenmodel.contract.BaseUiEffect
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

interface FlowWorkProcessor<C: WorkCommand,A: BaseAction,E: BaseUiEffect>{
    suspend fun work(command : C) : FlowWorkResult<A,E>

    @OptIn(ExperimentalCoroutinesApi::class)
    fun <L: DomainFailures,D1,D2> Flow<Either<L,D1>>.flatMapLatestWithResult(
        secondFlow : Flow<Either<L,D2>>,
        onError : suspend (L) -> E,
        onData : suspend  (D1,D2) -> A,
    ): FlowWorkResult<A,E>{
        return flatMapLatest { eitherFirst ->
            eitherFirst.handleAndGet(
                onLeftAction = {
                    flowOf(EffectResult(onError(it)))
                },
                onRightAction = {dataFirst ->
                    secondFlow.map { eitherSecond ->
                        eitherSecond.handleAndGet(
                            onLeftAction = {
                                EffectResult(onError(it))
                            },
                            onRightAction = {dataSecond ->
                                ActionResult(onData(dataFirst,dataSecond))

                            }
                        )

                    }

                }
            )

        }
    }


}

typealias FlowWorkResult<A,E> = Flow<WorkResult<A,E>>