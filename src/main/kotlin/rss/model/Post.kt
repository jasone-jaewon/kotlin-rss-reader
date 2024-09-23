package rss.model

import java.time.ZonedDateTime

class Post(
    private val title: String,
    private val link: String,
    private val createdAt: ZonedDateTime,
    private val createdBy: String,
)