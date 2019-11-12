package com.coding.wk.footballapplication.contextprovider

import com.coding.wk.footballapplication.utils.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.coroutines.CoroutineContext

class ContextProvider: CoroutineContextProvider(){
    @ExperimentalCoroutinesApi
    override val main: CoroutineContext = Dispatchers.Unconfined
}