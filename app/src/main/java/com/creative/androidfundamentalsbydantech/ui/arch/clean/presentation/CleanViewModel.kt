package com.creative.androidfundamentalsbydantech.ui.arch.clean.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creative.androidfundamentalsbydantech.ui.arch.clean.domain.WishlistItem
import com.creative.androidfundamentalsbydantech.ui.arch.clean.domain.usecase.AddWishlistItemUseCase
import com.creative.androidfundamentalsbydantech.ui.arch.clean.domain.usecase.ObserveWishlistUseCase
import com.creative.androidfundamentalsbydantech.ui.arch.clean.domain.usecase.RemoveWishlistItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CleanUiState(
    val items: List<WishlistItem> = emptyList(),
    val error: String? = null,
)

@HiltViewModel
class CleanViewModel @Inject constructor(
    observeWishlist: ObserveWishlistUseCase,
    private val addItem: AddWishlistItemUseCase,
    private val removeItem: RemoveWishlistItemUseCase,
) : ViewModel() {

    private val errors = MutableStateFlow<String?>(null)

    val state: StateFlow<CleanUiState> = combine(observeWishlist(), errors) { items, err ->
        CleanUiState(items = items, error = err)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), CleanUiState())

    fun add(label: String, priority: Int) {
        viewModelScope.launch {
            addItem(label, priority)
                .onFailure { errors.value = it.message ?: it::class.java.simpleName }
                .onSuccess { errors.value = null }
        }
    }

    fun remove(item: WishlistItem) {
        viewModelScope.launch { removeItem(item.id) }
    }

    fun dismissError() { errors.value = null }
}
