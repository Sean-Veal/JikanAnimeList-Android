package com.sean.jikananimelist.model.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteJAnime(
    @param:Json(name = "mal_id") val id: Int,
    @param:Json(name = "url") val malUrl: String,
    val images: AnimeImage,
    val trailer: AnimeTrailer,
    @param:Json(name = "approved") val isApproved: Boolean,
    val titles: List<AnimeTitle>,
    val type: String?,
    val source: String?,
    val episodes: Int?,
    val status: String?,
    @param:Json(name = "airing") val isAiring: Boolean,
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
    val broadcast: AnimeBroadcast,
    val producers: List<AnimeProducer>,
    val licensors: List<AnimeLicensor>,
    val studios: List<AnimeStudio>,
    val genres: List<AnimeGenre>,
    @param:Json(name = "explicit_genres") val explicitGenres: List<ExplicitAnimeGenre>,
    val demographics: List<AnimeDemographics>
) {
    data class AnimeDemographics(
        @param:Json(name = "mal_id") val id: Int,
        val type: String,
        val name: String,
        @param:Json(name = "url") val malUrl: String
    )
    data class ExplicitAnimeGenre(
        @param:Json(name = "mal_id") val id: Int,
        val type: String,
        val name: String,
        @param:Json(name = "url") val malUrl: String
    )
    data class AnimeGenre(
        @param:Json(name = "mal_id") val id: Int,
        val type: String,
        val name: String,
        @param:Json(name = "url") val malUrl: String
    )
    data class AnimeStudio(
        @param:Json(name = "mal_id") val id: Int,
        val type: String,
        val name: String,
        @param:Json(name = "url") val malUrl: String
    )
    data class AnimeLicensor(
        @param:Json(name = "mal_id") val id: Int,
        val type: String,
        val name: String,
        @param:Json(name = "url") val malUrl: String
    )
    data class AnimeProducer(
        @param:Json(name = "mal_id") val id: Int,
        val type: String,
        val name: String,
        @param:Json(name = "url") val malUrl: String
    )
    data class AnimeBroadcast(
        @param:Json(name = "string") val detail: String?
    )
    data class RemoteAnimeTime(
        @param:Json(name = "from") val fromDate: String?,
        @param:Json(name = "to") val toDate: String?
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