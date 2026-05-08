package com.creative.androidfundamentalsbydantech.ui.arch.clean.domain

import kotlinx.coroutines.flow.Flow

/** Repository interface lives in the domain layer so domain does not
 *  depend on any data implementation. */
interface WishlistRepository {
    fun observe(): Flow<List<WishlistItem>>
    suspend fun add(label: String, priority: Int)
    suspend fun remove(id: Long)
    suspend fun clear()
}
