package com.valentinhinov.ganandroidtest.testutils

import com.valentinhinov.ganandroidtest.di.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TestCoroutineContextProvider : CoroutineContextProvider {
    override val main: CoroutineContext
        get() = Dispatchers.Unconfined
    override val io: CoroutineContext
        get() = Dispatchers.Unconfined

}