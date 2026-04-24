package com.creative.androidfundamentalsbydantech.ui.data.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.androidfundamentalsbydantech.data.remote.GithubApi
import com.creative.androidfundamentalsbydantech.data.remote.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface NetworkUiState {
    data object Idle : NetworkUiState
    data object Loading : NetworkUiState
    data class Success(val repos: List<Repo>, val total: Int) : NetworkUiState
    data class Error(val message: String) : NetworkUiState
}

@HiltViewModel
class NetworkViewModel @Inject constructor(
    private val api: GithubApi,
) : ViewModel() {

    private val _state = MutableStateFlow<NetworkUiState>(NetworkUiState.Idle)
    val state: StateFlow<NetworkUiState> = _state.asStateFlow()

    private var inFlight: Job? = null

    fun search(query: String) {
        inFlight?.cancel()
        if (query.isBlank()) {
            _state.value = NetworkUiState.Idle
            return
        }
        inFlight = viewModelScope.launch {
            _state.value = NetworkUiState.Loading
            try {
                val response = api.searchRepositories(query = query, perPage = 20, page = 1)
                _state.value = NetworkUiState.Success(response.items, response.total_count)
            } catch (t: Throwable) {
                _state.value = NetworkUiState.Error(t.message ?: t::class.java.simpleName)
            }
        }
    }
}
