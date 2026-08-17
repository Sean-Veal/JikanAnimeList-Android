package com.sean.jikananimelist.repository

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

    override suspend fun getTopAnime(filter: String?, pageNumber: Int, pageSize: Int):
            ApiOperation<JAnimeResponse> = ApiUtil.safeApiCall {
            client.getTopAnime(filter, pageNumber, pageSize)
        }.mapSuccess { response ->
            val localList = response.data.map { JAnime(it) }
            JAnimeResponse(response.pageInfo, localList)
        }
}