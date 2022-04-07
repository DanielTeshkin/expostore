package com.expostore.ui.state

/**
 * @author Fedotov Yakov
 */
sealed class SearchState {
    data class Loading(val isLoading: Boolean) : SearchState()
    data class Error(val throwable: Throwable) : SearchState()
    data class SelectFavorite(val id: String, val isSuccess: Boolean) : SearchState()
}