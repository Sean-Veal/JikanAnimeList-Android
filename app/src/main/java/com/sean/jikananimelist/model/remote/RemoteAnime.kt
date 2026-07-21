package com.sean.jikananimelist.model.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteAnime(
    @param:Json(name = "mal_id") val id: Int,
    @param:Json(name = "url") val malUrl: String,
    val images: AnimeImage,
    val trailer: AnimeTrailer,
    val approved: Boolean,
    val titles: List<AnimeTitle>,
    val type: String?,
    val source: String?,
    val episodes: Int?,
    val status: String?,
    val airing: Boolean,
    val aired: RemoteAnimeTime,
    val duration: String?,
    val rating: String?,
    val score: Float?,
    @param:Json(name = "scored_by") val scoredBy: Int?,
    val rank: Int?,
    val popularity: Int?,
    val members: Int?,
    val favorites: Int?,
    val synopsis: String?,
    val background: String?,
    val season: String?,
    val year: Int?,
    val broadcast
) {
    data class RemoteAnimeTime(
        @param:Json(name = "from") val fromDate: String,
        @param:Json(name = "to") val toDate: String
    )
    data class AnimeTitle(
        val type: String,
        val title: String
    )
    data class AnimeImage(
        val jpg: JpgImage
    ) {
        data class JpgImage(
            @param:Json(name = "small_image_url") val imageUrl: String?
        )
    }

    data class AnimeTrailer(
        @param:Json(name = "url") val youtubeUrl: String?
    )
}