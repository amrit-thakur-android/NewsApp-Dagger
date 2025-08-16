package com.amritthakur.newsapp.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TestDispatcherProvider : DispatcherProvider {

    override val main: CoroutineDispatcher = Dispatchers.Unconfined

    override val io: CoroutineDispatcher = Dispatchers.Unconfined

    override val default: CoroutineDispatcher = Dispatchers.Unconfined

    override val unconfined: CoroutineDispatcher = Dispatchers.Unconfined
}
