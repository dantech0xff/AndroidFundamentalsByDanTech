package com.creative.androidfundamentalsbydantech.ui.data.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.creative.androidfundamentalsbydantech.data.paging.GithubPagingSource
import com.creative.androidfundamentalsbydantech.data.remote.GithubApi
import com.creative.androidfundamentalsbydantech.data.remote.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
class PagingViewModel @Inject constructor(
    private val api: GithubApi,
) : ViewModel() {

    val query = MutableStateFlow("android compose")

    val repos: Flow<PagingData<Repo>> = query.flatMapLatest { q ->
        Pager(
            config = PagingConfig(pageSize = 20, initialLoadSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { GithubPagingSource(api, q) },
        ).flow
    }.cachedIn(viewModelScope)

    fun updateQuery(q: String) {
        query.value = q
    }
}
