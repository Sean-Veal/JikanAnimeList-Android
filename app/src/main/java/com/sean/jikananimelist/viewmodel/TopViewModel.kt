package com.sean.jikananimelist.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sean.jikananimelist.model.JAnime
import com.sean.jikananimelist.model.JAnimeResponse
import com.sean.jikananimelist.repository.TopRepository
import com.sean.jikananimelist.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopViewModel @Inject constructor(
    private val repository: TopRepository
): ViewModel() {

    val topAnimeStateFlow: Flow<PagingData<JAnime>> =
        repository.getTopAnimePager().flow.cachedIn(viewModelScope)

}