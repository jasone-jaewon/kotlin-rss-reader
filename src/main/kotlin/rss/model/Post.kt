package rss.model

import java.time.ZonedDateTime

data class Post(
    val title: String,
    val link: String,
    val createdAt: ZonedDateTime,
    val createdBy: String?,
)