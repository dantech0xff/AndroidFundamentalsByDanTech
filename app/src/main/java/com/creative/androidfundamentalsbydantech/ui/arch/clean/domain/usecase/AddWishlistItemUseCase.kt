package com.creative.androidfundamentalsbydantech.ui.arch.clean.domain.usecase

import com.creative.androidfundamentalsbydantech.ui.arch.clean.domain.WishlistRepository
import javax.inject.Inject

/** Use case encapsulates one business action + validation. */
class AddWishlistItemUseCase @Inject constructor(
    private val repository: WishlistRepository,
) {
    suspend operator fun invoke(label: String, priority: Int): Result<Unit> {
        val trimmed = label.trim()
        if (trimmed.isEmpty()) return Result.failure(IllegalArgumentException("label empty"))
        if (priority !in 1..5) return Result.failure(IllegalArgumentException("priority 1..5"))
        repository.add(trimmed, priority)
        return Result.success(Unit)
    }
}
