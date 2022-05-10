package com.expostore.ui.fragment.chats.general

import kotlinx.coroutines.flow.Flow

interface Dialog {
     fun <T> singleSubscribe(flow: Flow<T>, action: suspend (T) -> Unit)




}
