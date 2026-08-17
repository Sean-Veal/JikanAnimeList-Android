package com.sean.jikananimelist.util

sealed interface UIState<T> {
    data object Loading: UIState<Nothing>
    data class Success<T>(val data: T): UIState<T>
    data class Error(val exception: Exception): UIState<Nothing>
}