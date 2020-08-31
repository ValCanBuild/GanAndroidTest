package com.valentinhinov.ganandroidtest.di

import com.valentinhinov.ganandroidtest.feature.browser.BrowserActivity

val allModules = listOf(CoroutineModule.instance, ApiModule.instance, BrowserActivity.activityModule)
