package util

import rss.model.Post
import java.time.ZonedDateTime

fun testPost(
    title: String = "",
    link: String = "",
    createdAt: ZonedDateTime = ZonedDateTime.now(),
    creator: String? = ""
) = Post(
    title = title,
    link = link,
    createdAt = createdAt,
    createdBy = creator
)