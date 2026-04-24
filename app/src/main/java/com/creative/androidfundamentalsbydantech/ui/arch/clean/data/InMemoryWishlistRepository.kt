package com.creative.androidfundamentalsbydantech.ui.arch.clean.data

import com.creative.androidfundamentalsbydantech.ui.arch.clean.domain.WishlistItem
import com.creative.androidfundamentalsbydantech.ui.arch.clean.domain.WishlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

/** Data layer: one concrete implementation of WishlistRepository.
 *  In a real app this would wrap Room/DataStore/Retrofit — here, a
 *  Flow-backed list demonstrates how the domain layer stays decoupled. */
@Singleton
class InMemoryWishlistRepository @Inject constructor() : WishlistRepository {

    private val ids = AtomicLong(0)
    private val _items = MutableStateFlow<List<WishlistItem>>(emptyList())

    override fun observe(): Flow<List<WishlistItem>> = _items.asStateFlow()

    override suspend fun add(label: String, priority: Int) {
        _items.value = _items.value + WishlistItem(
            id = ids.incrementAndGet(),
            label = label,
            priority = priority,
        )
    }

    override suspend fun remove(id: Long) {
        _items.value = _items.value.filterNot { it.id == id }
    }

    override suspend fun clear() {
        _items.value = emptyList()
    }
}
