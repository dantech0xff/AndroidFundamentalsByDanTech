package com.creative.androidfundamentalsbydantech.ui.arch.clean.domain.usecase

import com.creative.androidfundamentalsbydantech.ui.arch.clean.domain.WishlistRepository
import javax.inject.Inject

class RemoveWishlistItemUseCase @Inject constructor(
    private val repository: WishlistRepository,
) {
    suspend operator fun invoke(id: Long) = repository.remove(id)
}
