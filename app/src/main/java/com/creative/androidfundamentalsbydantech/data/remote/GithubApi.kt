package com.creative.androidfundamentalsbydantech.data.remote

import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query

@Serializable
data class RepoSearchResponse(
    val total_count: Int = 0,
    val incomplete_results: Boolean = false,
    val items: List<Repo> = emptyList(),
)

@Serializable
data class Repo(
    val id: Long,
    val name: String,
    val full_name: String,
    val description: String? = null,
    val stargazers_count: Int = 0,
    val forks_count: Int = 0,
    val language: String? = null,
    val html_url: String,
)

interface GithubApi {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1,
    ): RepoSearchResponse
}
