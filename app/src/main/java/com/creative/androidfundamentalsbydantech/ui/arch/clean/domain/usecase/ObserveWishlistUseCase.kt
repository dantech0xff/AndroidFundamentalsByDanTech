package com.creative.androidfundamentalsbydantech.ui.arch.clean.domain.usecase

import com.creative.androidfundamentalsbydantech.ui.arch.clean.domain.WishlistItem
import com.creative.androidfundamentalsbydantech.ui.arch.clean.domain.WishlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveWishlistUseCase @Inject constructor(
    private val repository: WishlistRepository,
) {
    operator fun invoke(): Flow<List<WishlistItem>> = repository.observe()
        .map { list -> list.sortedBy { it.priority } }
}
