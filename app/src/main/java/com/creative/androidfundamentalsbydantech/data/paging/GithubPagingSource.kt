package com.creative.androidfundamentalsbydantech.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.creative.androidfundamentalsbydantech.data.remote.GithubApi
import com.creative.androidfundamentalsbydantech.data.remote.Repo

class GithubPagingSource(
    private val api: GithubApi,
    private val query: String,
) : PagingSource<Int, Repo>() {

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        val anchor = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchor) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val page = params.key ?: 1
        return try {
            if (query.isBlank()) {
                return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
            }
            val response = api.searchRepositories(
                query = query,
                perPage = params.loadSize.coerceAtMost(50),
                page = page,
            )
            LoadResult.Page(
                data = response.items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.items.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
