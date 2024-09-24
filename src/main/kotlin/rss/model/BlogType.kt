package rss.model

enum class BlogType(
    val url: String,
) {
    LINE("https://techblog.lycorp.co.jp/ko/feed/index.xml"),
    WOOWA("https://techblog.woowahan.com/feed"),
    DEV_SISTERS("https://tech.devsisters.com/rss.xml")
}