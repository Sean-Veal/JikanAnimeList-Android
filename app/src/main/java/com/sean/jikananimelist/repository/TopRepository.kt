package com.sean.jikananimelist.repository

import androidx.collection.LruCache
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sean.jikananimelist.model.JAnime
import com.sean.jikananimelist.model.JAnimeResponse
import com.sean.jikananimelist.network.JAnimeClient
import com.sean.jikananimelist.util.ApiUtil
import javax.inject.Inject

interface TopRepository {
    suspend fun getTopAnime(filter: String?, pageNumber: Int, pageSize: Int):
            ApiOperation<JAnimeResponse>
    fun getTopAnimePager(): Pager<Int, JAnime>
}

class TopRepositoryImpl @Inject constructor(
    private val client: JAnimeClient
): TopRepository, PagingSource<Int, JAnime>() {

    val cache = LruCache<Int, JAnimeResponse>(1)

    override suspend fun getTopAnime(filter: String?, pageNumber: Int, pageSize: Int):
            ApiOperation<JAnimeResponse> {
        val cached = cache.snapshot()
        val cachedPage = cached.keys.firstOrNull()

        if (cachedPage != null && cachedPage >= pageNumber)
            return ApiOperation.Success(cache[pageNumber]!!)

        val domainResponse = ApiUtil.safeApiCall {
            client.getTopAnime(filter, pageNumber, pageSize)
        }.mapSuccess { response ->
            val localList = response.data.map { JAnime(it) }
            JAnimeResponse(response.pageInfo, localList)
        }
        domainResponse.onSuccess { response ->
            val previousData = cache[pageNumber]
            val combined = previousData?.let {
                JAnimeResponse(it.pageInfo, it.data + response.data)
            } ?: response
            cache.put(pageNumber, combined)
        }
        return domainResponse
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, JAnime> {
        val nextPageNumber = params.key ?: 1
        val response = this.getTopAnime(null, nextPageNumber, params.loadSize)
        var result: LoadResult<Int, JAnime> = LoadResult.Invalid()
        response.onSuccess { data ->
            result = LoadResult.Page(
                data.data,
                null,
                nextPageNumber+1
            )
        }.onFailure { exception ->
            result = LoadResult.Error(exception)
        }
        return result
    }

    override fun getRefreshKey(state: PagingState<Int, JAnime>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override fun getTopAnimePager(): Pager<Int, JAnime> =
        Pager(
            config = PagingConfig(20, initialLoadSize = 20, enablePlaceholders = true),
            pagingSourceFactory = {
                this
            }
        )

}