package com.expostore.ui.base.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.expostore.ui.state.ResponseState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


abstract class BaseViewModel : ViewModel() {

    private val _navigation = MutableSharedFlow<NavDirections>()
    val navigation = _navigation.asSharedFlow()

    private var isStarted: Boolean = false

    lateinit var navController: NavController

    protected abstract fun onStart()



    fun start() {
        if (!isStarted) {
            isStarted = true
            onStart()
        }
    }

    fun start(navController: NavController) {
        this.navController = navController
        if (!isStarted) {
            isStarted = true
            onStart()
        }
    }

    protected fun <T> Flow<T>.handleResult(
        flow: MutableStateFlow<ResponseState<T>>,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        onStart { flow.emit(ResponseState.Loading(true)) }
            .onEach {
                flow.emit(ResponseState.Success(it))
                onSuccess?.invoke(it)
            }
            .catch {
                flow.emit(ResponseState.Error(it))
                onError?.invoke(it)
            }
            .onCompletion { flow.emit(ResponseState.Loading(false)) }
            .launchIn(viewModelScope)
    }

    protected fun <T> Flow<T>.handleResult(
        flow: MutableSharedFlow<ResponseState<T>>,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        onStart { flow.emit(ResponseState.Loading(true)) }
            .onEach {
                flow.emit(ResponseState.Success(it))
                onSuccess?.invoke(it)
            }
            .catch {
                flow.emit(ResponseState.Error(it))
                onError?.invoke(it)
            }
            .onCompletion { flow.emit(ResponseState.Loading(false)) }
            .launchIn(viewModelScope)
    }

    protected fun <T> Flow<T>.handleResult(
        onLoading: ((Boolean) -> Unit)? = null,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        onStart { onLoading?.invoke(true) }
            .onEach {
                onSuccess?.invoke(it)
            }
            .catch {
                onError?.invoke(it)
            }
            .onCompletion { onLoading?.invoke(false) }
            .launchIn(viewModelScope)
    }

    protected fun <T> emit(flow: MutableStateFlow<T>, value: T) {
        viewModelScope.launch {
            flow.emit(value)
        }
    }

    protected fun <T> emit(flow: MutableSharedFlow<T>, value: T) {
        viewModelScope.launch {
            flow.emit(value)
        }
    }


    protected fun navigationTo(directions: NavDirections) {
        emit(_navigation, directions)
    }

    open fun back(){
        navController.popBackStack()
    }


}