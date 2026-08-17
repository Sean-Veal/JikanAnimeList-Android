package com.sean.jikananimelist.model

import com.sean.jikananimelist.model.remote.JPageInfo
import com.sean.jikananimelist.model.remote.RemoteJAnime

data class JAnimeResponse(
val pageInfo: JPageInfo,
val data: List<JAnime>
)