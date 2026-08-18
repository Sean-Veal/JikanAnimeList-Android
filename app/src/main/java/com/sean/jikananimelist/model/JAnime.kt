package com.sean.jikananimelist.model

import com.sean.jikananimelist.model.remote.RemoteJAnime
import com.sean.jikananimelist.model.remote.RemoteJAnime.AnimeDemographics
import com.sean.jikananimelist.model.remote.RemoteJAnime.AnimeGenre
import com.sean.jikananimelist.model.remote.RemoteJAnime.AnimeLicensor
import com.sean.jikananimelist.model.remote.RemoteJAnime.AnimeProducer
import com.sean.jikananimelist.model.remote.RemoteJAnime.AnimeStudio
import com.sean.jikananimelist.model.remote.RemoteJAnime.AnimeTitle
import com.sean.jikananimelist.model.remote.RemoteJAnime.ExplicitAnimeGenre
import com.sean.jikananimelist.model.remote.RemoteJAnime.RemoteAnimeTime
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

data class JAnime(
    val id: Int,
    val malUrl: String,
    val imageUrl: String?,
    val youtubeTrailerUrl: String?,
    val isApproved: Boolean,
    val titles: List<AnimeTitle>,
    val type: JAnimeType?,
    val source: String?,
    val episodes: Int?,
    val status: JAiringStatus?,
    val isAiring: Boolean,
    val aired: AnimeAiredDate,
    val duration: String?,
    val rating: JAnimeRating?,
    val score: Float?,
    val scoredBy: Int?,
    val rank: Int?,
    val popularity: Int?,
    val members: Int?,
    val favorites: Int?,
    val synopsis: String?,
    val background: String?,
    val season: JAnimeSeason?,
    val year: Int?,
    val broadcastDetails: String?,
    val producers: List<AnimeProducer>,
    val licensors: List<AnimeLicensor>,
    val studios: List<AnimeStudio>,
    val genres: List<AnimeGenre>,
    val explicitGenres: List<ExplicitAnimeGenre>,
    val demographics: List<AnimeDemographics>
) {
    constructor(remote: RemoteJAnime): this(
        id = remote.id,
        malUrl = remote.malUrl,
        imageUrl = remote.images.jpg.imageUrl,
        youtubeTrailerUrl = remote.trailer.youtubeUrl,
        isApproved = remote.isApproved,
        titles = remote.titles,
        type = JAnimeType.fromValue(remote.type),
        source = remote.source,
        episodes = remote.episodes,
        status = JAiringStatus.fromValue(remote.status),
        isAiring = remote.isAiring,
        aired = AnimeAiredDate(remote.aired),
        duration = remote.duration,
        rating = JAnimeRating.fromValue(remote.rating),
        score = remote.score,
        scoredBy = remote.scoredBy,
        rank = remote.rank,
        popularity = remote.popularity,
        members = remote.members,
        favorites = remote.favorites,
        synopsis = remote.synopsis,
        background = remote.background,
        season = JAnimeSeason.fromValue(remote.season),
        year = remote.year,
        broadcastDetails = remote.broadcast.detail,
        producers = remote.producers,
        licensors = remote.licensors,
        studios = remote.studios,
        genres = remote.genres,
        explicitGenres = remote.explicitGenres,
        demographics = remote.demographics
    )
}

data class AnimeAiredDate(
    val fromDate: LocalDateTime?,
    val toDate: LocalDateTime?
) {
    constructor(remote: RemoteAnimeTime): this(
        fromDate = remote.fromDate?.let { Instant.parse(it).toLocalDateTime(TimeZone.UTC) },
        toDate = remote.toDate?.let { Instant.parse(it).toLocalDateTime(TimeZone.UTC) }
    )
}

enum class JAnimeSeason(val value: String) {
    SUMMER("summer"),
    WINTER("winter"),
    SPRING("spring"),
    FALL("fall");

    companion object {
        fun fromValue(value: String?): JAnimeSeason? = entries.firstOrNull { it.value == value }
    }
}

enum class JAnimeRating(val value: String) {
    ALL_AGES("G - All Ages"),
    CHILDREN("PG - Children"),
    TEEN("PG-13 - Teens 13 or older"),
    R("R - 17+ (violence & profanity)"),
    R_PLUS("R+ - Mild Nudity"),
    HENTAI("Rx - Hentai");

    companion object {
        fun fromValue(value: String?): JAnimeRating? = entries.firstOrNull { it.value == value }
    }
}

enum class JAiringStatus(val value: String) {
    FINISHED("Finished Airing"),
    CURRENT("Currently Airing"),
    NOT_AIRING("Not yet aired");

    companion object {
        fun fromValue(value: String?): JAiringStatus? = entries.firstOrNull { it.value == value }
    }
}

enum class JAnimeType(val value: String) {
    TV("TV"),
    OVA("OVA"),
    MOVIE("Movie"),
    SPECIAL("Special"),
    ONA("ONA"),
    MUSIC("Music"),
    CM("CM"),
    PV("PV"),
    TV_SPECIAL("TV Special");

    companion object {
        fun fromValue(value: String?): JAnimeType? = entries.firstOrNull { it.value == value }

    }
}

