package com.example.utils.manager

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface CoroutineManager{

    fun runOnBackground(
        scope : CoroutineScope,
        block : CoroutineBlock
    ): Job

    fun runOnUi(scope : CoroutineScope,block: CoroutineBlock): Job

    suspend fun changeFlow(coroutineFlow: CoroutineFlow,block: CoroutineBlock)

    abstract class Abstract(
        private val backgroundDispatcher : CoroutineDispatcher,
        private val uiDispatcher : CoroutineDispatcher
    ): CoroutineManager{

        override fun runOnBackground(scope: CoroutineScope, block: CoroutineBlock): Job {
            return scope.launch(context = backgroundDispatcher,block = block)
        }

        override fun runOnUi(scope: CoroutineScope, block: CoroutineBlock): Job {
            return scope.launch(context = uiDispatcher,block= block)
        }

        override suspend fun changeFlow(coroutineFlow: CoroutineFlow, block: CoroutineBlock) {
            val dispatcher = when(coroutineFlow){
                CoroutineFlow.BACKGROUND -> backgroundDispatcher
                CoroutineFlow.UI -> uiDispatcher
            }
            withContext(context = dispatcher,block = block)
        }
    }
    class Base: Abstract(backgroundDispatcher = Dispatchers.IO, uiDispatcher = Dispatchers.Main)

}

typealias CoroutineBlock = suspend CoroutineScope.() -> Unit
enum class CoroutineFlow{
    BACKGROUND,UI
}