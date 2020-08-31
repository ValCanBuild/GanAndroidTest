package com.valentinhinov.ganandroidtest.testutils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

@ExperimentalCoroutinesApi
class TestCoroutineRule : TestRule {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testCoroutineDispatcher)

    override fun apply(base: Statement?, description: Description?): Statement =
        object: Statement() {
            override fun evaluate() {
                Dispatchers.setMain(testCoroutineDispatcher)

                base?.evaluate()

                Dispatchers.resetMain()
                testCoroutineDispatcher.cleanupTestCoroutines()
            }
        }

    fun runBlocking(block: suspend TestCoroutineScope.() -> Unit) =
        testCoroutineScope.runBlockingTest { block() }
}