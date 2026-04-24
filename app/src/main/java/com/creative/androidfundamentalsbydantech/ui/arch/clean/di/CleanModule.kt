package com.creative.androidfundamentalsbydantech.ui.arch.clean.di

import com.creative.androidfundamentalsbydantech.ui.arch.clean.data.InMemoryWishlistRepository
import com.creative.androidfundamentalsbydantech.ui.arch.clean.domain.WishlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Bind domain interface to the data-layer implementation — classic
 *  dependency-inversion wiring. */
@Module
@InstallIn(SingletonComponent::class)
abstract class CleanModule {

    @Binds
    @Singleton
    abstract fun bindWishlistRepository(impl: InMemoryWishlistRepository): WishlistRepository
}
