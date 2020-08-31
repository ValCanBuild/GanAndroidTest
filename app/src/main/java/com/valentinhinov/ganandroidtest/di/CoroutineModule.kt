package com.valentinhinov.ganandroidtest.di

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module
import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {
    val main: CoroutineContext
    val io: CoroutineContext
}

object CoroutineModule {

    val instance = module {
        factory<CoroutineContextProvider> {
            object: CoroutineContextProvider {
                override val main: CoroutineContext
                    get() = Dispatchers.Main
                override val io: CoroutineContext
                    get() = Dispatchers.IO

            }
        }
    }
}