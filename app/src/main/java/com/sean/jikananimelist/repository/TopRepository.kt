package com.sean.jikananimelist.repository

import androidx.collection.LruCache
import com.sean.jikananimelist.model.JAnime
import com.sean.jikananimelist.model.JAnimeResponse
import com.sean.jikananimelist.network.JAnimeClient
import com.sean.jikananimelist.util.ApiUtil
import javax.inject.Inject

interface TopRepository {
    suspend fun getTopAnime(filter: String?, pageNumber: Int, pageSize: Int):
            ApiOperation<JAnimeResponse>
}

class TopRepositoryImpl @Inject constructor(
    private val client: JAnimeClient
): TopRepository {

    val cache = LruCache<Int, JAnimeResponse>(1)

    override suspend fun getTopAnime(filter: String?, pageNumber: Int, pageSize: Int):
            ApiOperation<JAnimeResponse> {
        val cached = cache.snapshot()
        val cachedPage = cached.keys.firstOrNull()

        if (cachedPage != null && cachedPage >= pageNumber) return ApiOperation.Success(cache[pageNumber]!!)

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

}