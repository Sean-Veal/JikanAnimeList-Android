package com.sean.jikananimelist.model.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteJAnimeResponse(
    @param:Json(name = "pagination") val pageInfo: JPageInfo,
    val data: List<RemoteJAnime>
)
