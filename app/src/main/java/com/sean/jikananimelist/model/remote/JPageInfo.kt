package com.sean.jikananimelist.model.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JPageInfo(
    @param:Json(name = "last_visible_page") val lastVisiblePage: Int,
    @param:Json(name = "has_next_page") val hasNextPage: Boolean,
    @param:Json(name = "current_page") val currentPage: Int
)
